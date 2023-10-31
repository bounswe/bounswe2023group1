package com.cmpe451.resq.data.remote

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val jwt: String,
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val roles: List<String>
)
data class RegisterRequest(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)
data class RegisterResponse(
    val message: String
)
interface AuthService {
    @POST("auth/signin")
    suspend fun login(@Body request: LoginRequest):  Response<LoginResponse>

    @POST("auth/signup")
    suspend fun register(@Body request: RegisterRequest): Response<ResponseBody>
}
class AuthApi {

    private val authService: AuthService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://16.16.63.194/resq/api/v1/") // TODO: Replace with backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }


    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return authService.login(loginRequest)
    }

    suspend fun register(registerRequest: RegisterRequest): Response<ResponseBody> {
        return authService.register(registerRequest)
    }
}
