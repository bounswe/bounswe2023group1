package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cmpe451.resq.data.remote.LoginResponse
import com.cmpe451.resq.domain.LoginUseCase
import com.cmpe451.resq.utils.NavigationItem
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private var loginUseCase = LoginUseCase()

    private val _loginResponse = mutableStateOf<LoginResponse?>(null)
    val loginResponse: State<LoginResponse?> = _loginResponse

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage


    fun login(email: String, password: String, navController: NavController, appContext: Context) {
        if (validateLoginInputs(email, password)) {
            viewModelScope.launch {
                val result = loginUseCase.execute(email, password, appContext)
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

    fun validateLoginInputs(email: String, password: String): Boolean {
        when {
            email.isBlank() -> {
                _errorMessage.value = "Email cannot be empty."
                return false
            }
            password.isBlank() -> {
                _errorMessage.value = "Password cannot be empty."
                return false
            }
            else -> {
                _errorMessage.value = null
                return true
            }
        }
    }
}
