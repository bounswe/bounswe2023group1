package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.LoginRequestBody
import com.cmpe451.resq.data.models.LoginResponse
import com.cmpe451.resq.data.remote.ResqService
import com.cmpe451.resq.utils.NavigationItem
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {
    private val _loginResponse = mutableStateOf<LoginResponse?>(null)
    val loginResponse: State<LoginResponse?> = _loginResponse

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun login(email: String, password: String, navController: NavController, appContext: Context) {

        if (validateLoginInputs(email, password)) {
            viewModelScope.launch {
                val result = getLoginResponse(email, password, appContext)
                if (result.isSuccess) {
                    _loginResponse.value = result.getOrNull()
                    _errorMessage.value = null

                    navController.navigate(NavigationItem.Map.route)
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message
                }
            }
        }
    }

    private suspend fun getLoginResponse(email: String, password: String, appContext: Context): Result<LoginResponse> {
        val api = ResqService(appContext)

        val response = api.login(LoginRequestBody(email, password))
        if (response.isSuccessful) {
            response.body()?.let {
                saveLoginResponse(it, appContext)
                return Result.success(it)
            }
        }
        return Result.failure(Throwable(response.message()))
    }
    private fun saveLoginResponse(loginResponse: LoginResponse, appContext: Context) {
        val userSessionManager = UserSessionManager.getInstance(appContext)

        userSessionManager.createLoginSession(
            token = loginResponse.jwt,
            userId = loginResponse.id,
            userName = loginResponse.name,
            userSurname = loginResponse.surname,
            userEmail = loginResponse.email,
            userRoles = loginResponse.roles
        )
    }

    private fun validateLoginInputs(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                _errorMessage.value = "Email cannot be empty."
                false
            }

            password.isBlank() -> {
                _errorMessage.value = "Password cannot be empty."
                false
            }

            else -> {
                _errorMessage.value = null
                true
            }
        }
    }
}
