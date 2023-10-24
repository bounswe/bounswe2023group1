package com.cmpe451.resq.domain

import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.LoginRequest
import com.cmpe451.resq.data.models.User

class LoginUseCase() {

    private val authApi = AuthApi()

    fun execute(email: String, password: String): Result<User> {
        val response = authApi.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
        }

        return Result.failure(Throwable(response.message()))
    }
}
