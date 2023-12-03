package com.cmpe451.resq.data.remote

import android.content.Context
import com.cmpe451.resq.data.Constants
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryNode
import com.cmpe451.resq.data.models.CreateNeedRequestBody
import com.cmpe451.resq.data.models.LoginRequestBody
import com.cmpe451.resq.data.models.LoginResponse
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.data.models.RegisterRequestBody
import com.cmpe451.resq.data.models.UserInfoResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface CategoryTreeNodeService {
    @GET("categorytreenode/getMainCategories")
    suspend fun getMainCategories(
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String
    ): Response<List<CategoryNode>>
}

interface NeedService {
    @POST("need/createNeed")
    suspend fun createNeed(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String,
        @Body requestBody: CreateNeedRequestBody
    ): Response<Int>
}

interface AuthService {
    @POST("auth/signin")
    suspend fun login(@Body requestBody: LoginRequestBody):  Response<LoginResponse>

    @POST("auth/signup")
    suspend fun register(@Body requestBody: RegisterRequestBody): Response<ResponseBody>
}

interface ProfileService {
    @GET("user/getUserInfo")
    suspend fun getUserInfo(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String
    ): Response<UserInfoResponse>
}

class ResqService(appContext: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BACKEND_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val categoryTreeNodeService: CategoryTreeNodeService = retrofit.create(CategoryTreeNodeService::class.java)
    private val needService: NeedService = retrofit.create(NeedService::class.java)
    private val authService: AuthService = retrofit.create(AuthService::class.java)
    private val profileService: ProfileService = retrofit.create(ProfileService::class.java)

    private val userSessionManager: UserSessionManager = UserSessionManager.getInstance(appContext)

    // Category Tree Node methods
    suspend fun getMainCategories(): Response<List<CategoryNode>> {
        val token = userSessionManager.getUserToken() ?: ""
        val selectedRole = userSessionManager.getSelectedRole() ?: ""

        return categoryTreeNodeService.getMainCategories(
            jwtToken = "Bearer $token",
            role = selectedRole
        )
    }

    // Need methods
    suspend fun createNeed(request: CreateNeedRequestBody): Response<Int> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""
        val selectedRole = userSessionManager.getSelectedRole() ?: ""

        return needService.createNeed(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole,
            requestBody = request
        )
    }

    // Auth methods
    suspend fun login(request: LoginRequestBody): Response<LoginResponse> = authService.login(request)
    suspend fun register(request: RegisterRequestBody): Response<ResponseBody> = authService.register(request)

    // Profile methods
    suspend fun getUserInfo(): ProfileData {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""
        val selectedRole = userSessionManager.getSelectedRole() ?: ""

        val response = profileService.getUserInfo(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole
        )

        return ProfileData(
            name = response.body()?.name, surname = response.body()?.surname,
            email = response.body()?.email,
            roles = response.body()?.roles, selectedRole = selectedRole,
            year = "1990", month = "05", day = "29",
            city = "Istanbul", country = "Turkey",
            gender = "Female", bloodType = "0 rh-", height = "180", weight = "80",
            phoneNumber = "05321234567", state = "Kadikoy",
        )
    }
}
