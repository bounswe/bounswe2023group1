package com.cmpe451.resq.domain

import android.content.Context
import android.provider.Settings.Global.putInt
import android.provider.Settings.Global.putString
import androidx.compose.ui.platform.LocalContext
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
        val sharedPref = appContext.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        val gson = Gson()
        val rolesJson = gson.toJson(loginResponse.roles)

        sharedPref.edit().apply {
            putString("JWT_TOKEN", loginResponse.jwt)
            putInt("USER_ID", loginResponse.id)
            putString("USER_NAME", loginResponse.name)
            putString("USER_SURNAME", loginResponse.surname)
            putString("USER_EMAIL", loginResponse.email)
            putString("USER_ROLES", rolesJson)
            apply()
        }
    }
}
