package com.cmpe451.resq.domain

import android.content.Context
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.compose.ui.platform.LocalContext
import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.LoginRequest
import com.cmpe451.resq.data.remote.LoginResponse

class LoginUseCase() {

    private val authApi = AuthApi()

    suspend fun execute(email: String, password: String): Result<LoginResponse> {
        val response = authApi.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
        }

        return Result.failure(Throwable(response.message()))
    }
}
