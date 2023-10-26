package com.cmpe451.resq.domain

import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.RegisterRequest
import com.cmpe451.resq.data.remote.RegisterResponse

class RegistrationUseCase() {

    private val authApi = AuthApi()

    fun execute(email: String, password: String): Result<User> {
        val response = authApi.register(RegisterRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
        }
        return Result.failure(Throwable(response.message()))
    }
}
