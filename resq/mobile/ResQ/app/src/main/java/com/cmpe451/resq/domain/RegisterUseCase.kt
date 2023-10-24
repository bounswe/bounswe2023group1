package com.cmpe451.resq.domain

import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.RegisterRequest
import com.cmpe451.resq.data.remote.RegisterResponse

class RegisterUseCase(private val authApi: AuthApi) {
    suspend fun execute(email: String, password: String): Result<RegisterResponse> {
        val response = authApi.register(RegisterRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
        }
        return Result.failure(Throwable(response.message()))
    }
}
