package com.cmpe451.resq.data.remote

import androidx.compose.ui.res.stringResource
import com.cmpe451.resq.data.Constants
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

class ProfileRepository {

    private val profileService: ProfileService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BACKEND_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        profileService = retrofit.create(ProfileService::class.java)
    }

    suspend fun getUserData(): ProfileData{
        val response =  profileService.getUserInfo(
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbGl0cGMyNTI1QGdtYWlsLmNvbSIsImlhdCI6MTY5ODcwMzU5MiwiZXhwIjoxNjk4Nzg5OTkyfQ.FLyFCITkyCEYOuMBdaSHWCl5V1MrLSl5Yt5Y2L2WAlc",
            "Victim"
        )

        return ProfileData(
            name = response.body()?.name,
            surname = response.body()?.surname,
            dateOfBirth = "29/05/1993",
            role = "Victim",
            address = ""
            //address = "123 Main Street, apt 4B San Diego CA, 91911"
        )

        // This is a placeholder response
        /*
                return ProfileData(
                    name = "Responder",
                    surname = "Responderoğlu",
                    dateOfBirth = "01/01/1990",
                    role = "Responder" ,
                    address = "123 Main Street, apt 4B San Diego CA, 91911"
                    //address = null
                )
        */

        /*
                return ProfileData(
                    name = "Harun",
                    surname = "Ergen",
                    dateOfBirth = "18/10/2000",
                    role = "Victim" ,
                    //address = "123 Main Street, apt 4B San Diego CA, 91911"
                   address = null)
          */
    }}
