package com.cmpe451.resq.data.remote

import android.content.Context
import com.cmpe451.resq.data.Constants
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.ProfileData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileService {
    @GET("user/getUserInfo?userId=13")
    suspend fun getUserInfo(@Header("Authorization") jwtToken :String,
                            @Header("X-Selected-Role") role: String): Response<UserInfoResponse>
}

data class UserInfoResponse(
    val name: String,
    val surname: String,
    val email: String,
    val roles: List<String>
)

class ProfileRepository(appContext: Context) {

    private val profileService: ProfileService
    private val userSessionManager: UserSessionManager = UserSessionManager(appContext)

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        profileService = retrofit.create(ProfileService::class.java)
    }

    suspend fun getUserData(): ProfileData {
        val token = userSessionManager.getUserToken() ?: ""
        val selectedRole = userSessionManager.getSelectedRole() ?: ""

        val response = profileService.getUserInfo("Bearer $token", selectedRole)

        return ProfileData(
            name = response.body()?.name,
            surname = response.body()?.surname,
            email = response.body()?.email,
            roles = response.body()?.roles,
            selectedRole = selectedRole,
            year = "1990",
            month = "05",
            day = "29",
            city = "Istanbul",
            country = "Turkey",
            gender = "Female",
            bloodType = "0 rh-",
            height = "180",
            weight = "80",
            phoneNumber = "05321234567",
            state = "Kadikoy",
        )
    }

}
