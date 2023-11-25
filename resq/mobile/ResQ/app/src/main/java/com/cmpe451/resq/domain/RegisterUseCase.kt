package com.cmpe451.resq.domain

import android.content.Context
import com.cmpe451.resq.data.models.RegisterRequest
import com.cmpe451.resq.data.remote.ResqService

class RegistrationUseCase() {
    suspend fun execute(name: String, surname: String, email: String, password: String, appContext: Context): Result<String> {
        val api = ResqService(appContext)
        val response = api.register(RegisterRequest(name, surname, email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it.string())
            }
        }
        return Result.failure(Throwable(response.message()))
    }
}
