package com.cmpe451.resq.data.remote

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.cmpe451.resq.data.Constants
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryNode
import com.cmpe451.resq.data.models.CreateNeedRequestBody
import com.cmpe451.resq.data.models.LoginRequestBody
import com.cmpe451.resq.data.models.LoginResponse
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.data.models.RegisterRequestBody
import com.cmpe451.resq.data.models.UserInfoRequest
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.LocalDate
import java.time.format.DateTimeParseException

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
    @GET("profile/getProfileInfo")
    suspend fun getUserInfo(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String
    ): Response<ProfileData>



    @POST("profile/updateProfile")
    suspend fun updateProfile(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String,
        @Body request: UserInfoRequest
    ): Response<String>
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
    @RequiresApi(Build.VERSION_CODES.O)
    fun parseBirthDate(birthDate: String?): Triple<String, String, String>? {
        if (birthDate.isNullOrBlank()) {
            return null
        }
        return try {
            val date = LocalDate.parse(birthDate)
            val year = date.year.toString()
            val month = date.monthValue.toString()
            val day = date.dayOfMonth.toString()

            Triple(year, month, day)
        } catch (e: DateTimeParseException) {
            //TO DO Handle parsing error if needed
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserInfo(): ProfileData {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()
        val selectedRole = userSessionManager.getSelectedRole() ?: ""


        val response = profileService.getUserInfo(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole
        )

        return ProfileData(
            name = response.body()?.name,
            surname = response.body()?.surname,
            city = response.body()?.city,
            country = response.body()?.country,
            gender = response.body()?.gender,
            bloodType = response.body()?.bloodType,
            height = response.body()?.height,
            weight = response.body()?.weight,
            phoneNumber = response.body()?.phoneNumber,
            state = response.body()?.state,
            emailConfirmed = response.body()?.emailConfirmed,
            privacyPolicyAccepted = response.body()?.privacyPolicyAccepted,
            birthdate = response.body()?.birthdate.toString(),
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateUserData(profileData: ProfileData): Response<String> {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()
        val selectedRole = userSessionManager.getSelectedRole() ?: ""

        val request = UserInfoRequest(
            name = profileData.name ?: "",
            surname = profileData.surname ?: "",
            birthdate = null,
            country = profileData.country ?: "",
            city = profileData.city ?: "",
            state = profileData.state ?: "",
            bloodType = profileData.bloodType ?: "",
            height = profileData.height,
            weight = profileData.weight,
            gender = profileData.gender ?: "",
            phoneNumber = profileData.phoneNumber ?: "",
        )
        return profileService.updateProfile(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole,
            request = request
        )
    }
}
