package com.cmpe451.resq.data.remote

import com.cmpe451.resq.data.models.User
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val email: String, val password: String)
data class RegisterResponse(val success: Boolean, val message: String)
interface AuthService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<User>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>
}
class AuthApi {

    private val authService: AuthService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://resq.com/") // TODO: Replace with backend URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        authService = retrofit.create(AuthService::class.java)
    }

    //  suspend fun login(loginRequest: LoginRequest): Response<User> {
    //      return authService.login(loginRequest)
    //  }

    fun login(loginRequest: LoginRequest): Response<User> {
        // Dummy logic for now:
        return if (loginRequest.email == "test@email.com" && loginRequest.password == "password123") {
            Response.success(User(email = loginRequest.email, password = "password123"))
        } else {
            Response.error(401, "Invalid credentials".toResponseBody(null))
        }
    }


    suspend fun register(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authService.register(registerRequest)
    }
}

