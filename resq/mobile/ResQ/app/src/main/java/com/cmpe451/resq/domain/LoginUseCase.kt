package com.cmpe451.resq.domain

import android.content.Context
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.compose.ui.platform.LocalContext
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.remote.AuthApi
import com.cmpe451.resq.data.remote.LoginRequest
import com.cmpe451.resq.data.remote.LoginResponse
import com.google.gson.Gson

class LoginUseCase() {

    private val authApi = AuthApi()

    suspend fun execute(email: String, password: String, appContext: Context): Result<LoginResponse> {
        val response = authApi.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                saveLoginResponse(it, appContext)
                return Result.success(it)
            }
        }
        return Result.failure(Throwable(response.message()))
    }
    private fun saveLoginResponse(loginResponse: LoginResponse, appContext: Context) {
        val userSessionManager = UserSessionManager(appContext)

        userSessionManager.createLoginSession(
            token = loginResponse.jwt,
            userId = loginResponse.id,
            userName = loginResponse.name,
            userSurname = loginResponse.surname,
            userEmail = loginResponse.email,
            userRoles = loginResponse.roles
        )
    }
}
