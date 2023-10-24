package com.cmpe451.resq.domain

import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.RegisterRequest
import com.cmpe451.resq.data.remote.RegisterResponse
import retrofit2.Response

class RegisterUseCase(private val authApi: AuthApi) {
    suspend fun execute(email: String, password: String): Response<RegisterResponse> {
        return authApi.register(RegisterRequest(email, password))
    }
}
