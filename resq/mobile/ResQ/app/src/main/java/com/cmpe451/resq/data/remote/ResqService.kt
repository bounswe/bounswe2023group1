package com.cmpe451.resq.data.remote

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.cmpe451.resq.data.Constants
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.CreateNeedRequestBody
import com.cmpe451.resq.data.models.CreateResourceRequestBody
import com.cmpe451.resq.data.models.LoginRequestBody
import com.cmpe451.resq.data.models.LoginResponse
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.data.models.RegisterRequestBody
import com.cmpe451.resq.data.models.UserInfoRequest
import com.google.gson.GsonBuilder
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
        @Header("Authorization") jwtToken: String
    ): Response<List<CategoryTreeNode>>
}

interface ResourceService {
    @POST("resource/createResource")
    suspend fun createResource(
        @Header("Authorization") jwtToken: String,
        @Body requestBody: CreateResourceRequestBody
    ): Response<Int>
}
interface NeedService {
    @POST("need/createNeed")
    suspend fun createNeed(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Body requestBody: CreateNeedRequestBody
    ): Response<Int>


    @GET("need/filterByDistance")
    fun filterNeedByDistance(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("distance") distance: Double,
        @Header("Authorization") jwtToken: String,
    ): Response<List<Need>>

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
        @Header("Authorization") jwtToken: String
    ): Response<ProfileData>

    @POST("user/requestRole")
    suspend fun selectRole(
        @Query("userId") userId: Int,
        @Query("role") requestedRole: String,
        @Header("Authorization") jwtToken: String
    ): Response<String>


    @POST("profile/updateProfile")
    suspend fun updateProfile(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Body request: UserInfoRequest
    ): Response<String>

}

class ResqService(appContext: Context) {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BACKEND_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val categoryTreeNodeService: CategoryTreeNodeService = retrofit.create(CategoryTreeNodeService::class.java)
    private val resourceService: ResourceService = retrofit.create(ResourceService::class.java)
    private val needService: NeedService = retrofit.create(NeedService::class.java)
    private val authService: AuthService = retrofit.create(AuthService::class.java)
    private val profileService: ProfileService = retrofit.create(ProfileService::class.java)

    private val userSessionManager: UserSessionManager = UserSessionManager.getInstance(appContext)

    // Category Tree Node methods
    suspend fun getMainCategories(): Response<List<CategoryTreeNode>> {
        val token = userSessionManager.getUserToken() ?: ""

        return categoryTreeNodeService.getMainCategories(
            jwtToken = "Bearer $token"
        )
    }

    // Resource methods
    suspend fun createResource(request: CreateResourceRequestBody): Response<Int> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""

        request.senderId = userId

        return resourceService.createResource(
            jwtToken = "Bearer $token",
            requestBody = request
        )
    }

    // Need methods
    suspend fun createNeed(request: CreateNeedRequestBody): Response<Int> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""

        return needService.createNeed(
            userId = userId,
            jwtToken = "Bearer $token",
            requestBody = request
        )
    }

    suspend fun filterNeedByDistance(
        latitude: Double,
        longitude: Double,
        distance: Double
    ): Response<List<Need>> {
        val selectedRole = userSessionManager.getSelectedRole() ?: ""
        val token = userSessionManager.getUserToken() ?: ""
        return needService.filterNeedByDistance(
            latitude = latitude,
            longitude = longitude,
            distance = distance,
            jwtToken = "Bearer $token"
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

            return Triple(year, month, day)
        } catch (e: DateTimeParseException) {
            //TO DO Handle parsing error if needed
           null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserInfo(): ProfileData {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()

        val response = profileService.getUserInfo(
            userId = userId,
            jwtToken = "Bearer $token"
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
            birth_date = response.body()?.birth_date.toString(),
        )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateUserData(profileData: ProfileData): Response<String> {
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()

        val request = UserInfoRequest(
            name = profileData.name ?: "",
            surname = profileData.surname ?: "",
            birth_date = null,
            Country = profileData.country ?: "",
            City = profileData.city ?: "",
            State = profileData.state ?: "",
            bloodType = profileData.bloodType ?: "",
            height = profileData.height,
            weight = profileData.weight,
            gender = profileData.gender ?: "",
            phoneNumber = profileData.phoneNumber ?: "",
            isEmailConfirmed = true,
            isPrivacyPolicyAccepted = true
        )
        return profileService.updateProfile(
            userId = userId,
            jwtToken = "Bearer $token",
            request = request
        )
    }

    suspend fun selectRole(requestedRole: String): Response<String> {
        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""

        val response = profileService.selectRole(
            userId = userId,
            requestedRole = requestedRole,
            jwtToken = "Bearer $token"
        )

        return response
    }
}