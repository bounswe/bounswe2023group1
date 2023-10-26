package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    private var loginUseCase = LoginUseCase()

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun login(email: String, password: String) {
        if (validateLoginInputs(email, password)) {
            viewModelScope.launch {
                val result = loginUseCase.execute(email, password)
                if (result.isSuccess) {
                    _user.value = result.getOrNull()
                    _errorMessage.value = null
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
