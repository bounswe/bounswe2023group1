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
import com.cmpe451.resq.data.models.UserInfo
import com.cmpe451.resq.data.models.UserInfoRequest
import com.cmpe451.resq.data.models.RegisterRequestBody

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
    @GET("user/getUserInfo")
    suspend fun getUserInfo(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String
    ): Response<UserInfo>

    @POST("profile/updateProfile")
    suspend fun updateProfile(
        @Query("userId") userId: Int,
        @Header("Authorization") jwtToken: String,
        @Header("X-Selected-Role") role: String,
        @Body request: UserInfoRequest
    ): Response<ResponseBody>

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

        try {
            val date = LocalDate.parse(birthDate)
            val year = date.year.toString()
            val month = date.monthValue.toString()
            val day = date.dayOfMonth.toString()

            return Triple(year, month, day)
        } catch (e: DateTimeParseException) {
            //TO DO Handle parsing error if needed
            return null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getUserData(): ProfileData {
        val token = userSessionManager.getUserToken() ?: ""

        val userId = userSessionManager.getUserId()
        val token = userSessionManager.getUserToken() ?: ""
        val selectedRole = userSessionManager.getSelectedRole() ?: ""


        val response = profileService.getUserInfo(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole
        )

        val parsedDate = parseBirthDate(response.body()?.birth_date)
        val (parsedYear, parsedMonth, parsedDay) = parsedDate ?: Triple("", "", "")
        val profileData = ProfileData(
            name = response.body()?.name, surname = response.body()?.surname,
            email = response.body()?.email,
            roles = response.body()?.roles, selectedRole = selectedRole,
            year = parsedYear, month = parsedMonth, day = parsedDay,
            city = response.body()?.city, country = response.body()?.country,
            gender = response.body()?.gender, bloodType = response.body()?.bloodType, height = response.body()?.height.toString(), weight = response.body()?.weight.toString(),
            phoneNumber = response.body()?.phoneNumber, state = response.body()?.state,
            emailConfirmed = response.body()?.emailConfirmed, privacyPolicyAccepted = response.body()?.privacyPolicyAccepted

            )
        return profileData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateUserData(profileData: ProfileData): Response<ResponseBody>{
        val token = userSessionManager.getUserToken() ?: ""
        val userId = userSessionManager.getUserId()
        val selectedRole = userSessionManager.getSelectedRole() ?: ""
        val formattedBirthDate = profileData.getFormattedBirthDate()
        val request = UserInfoRequest(
            name =  profileData.name ?: "",
            surname = profileData.surname ?: "",
            email = profileData.email ?: "",
            roles = profileData.roles ?: listOf(),
            birth_date = formattedBirthDate,
            country = profileData.country ?: "",
            city = profileData.city ?: "",
            state = profileData.state ?: "",
            bloodType = profileData.bloodType ?: "",
            height = profileData.height?.toIntOrNull(),
            weight = profileData.weight?.toIntOrNull(),
            gender = profileData.gender ?: "",
            phoneNumber = profileData.phoneNumber ?: "",
        )
        val response = profileService.updateProfile(
            userId = userId,
            jwtToken = "Bearer $token",
            role = selectedRole,
            request = request
        )
        return response



    }


}
