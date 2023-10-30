package com.cmpe451.resq.domain

import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.RegisterRequest
import com.cmpe451.resq.data.remote.RegisterResponse

class RegistrationUseCase() {

    private val authApi = AuthApi()

    suspend fun execute(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<String> {

        val response = authApi.register(RegisterRequest(name, surname, email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it.string())
            }
        }
        return Result.failure(Throwable(response.message()))
    }
}
