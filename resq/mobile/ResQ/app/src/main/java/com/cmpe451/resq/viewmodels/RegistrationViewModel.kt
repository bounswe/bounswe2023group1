package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.RegistrationUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel() : ViewModel() {

    private var registrationUseCase = RegistrationUseCase()

    private val _user = mutableStateOf<User?>(null)
    val user: State<User?> = _user

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun register(email: String, password: String, confirmPassword: String, termsAccepted: Boolean) {
        if (validateRegistrationInputs(email, password, confirmPassword, termsAccepted)) {
            viewModelScope.launch {
                try {
                    val result = registrationUseCase.execute(email, password)
                    if (result.isSuccess) {
                        _user.value = result.getOrNull()
                        _errorMessage.value = null
                    }
                    else {
                        _errorMessage.value = result.exceptionOrNull()?.message
                            ?: "Registration failed. Please try again."
                    }
                } catch (e: Exception) {
                    _errorMessage.value = e.message ?: "Unexpected error occurred."
                }
            }
        }
    }
    private fun validateRegistrationInputs(email: String, password: String, confirmPassword: String, termsAccepted: Boolean): Boolean {
        if (email.isBlank()) {
            _errorMessage.value = "Email cannot be empty."
            return false
        }
        if (password.isBlank()) {
            _errorMessage.value = "Password cannot be empty."
            return false
        }
        if (password.length < 8) {
            _errorMessage.value = "Password should be at least 8 characters long."
            return false
        }
        if (password != confirmPassword) {
            _errorMessage.value = "Passwords do not match!"
            return false
        }
        if (!termsAccepted) {
            _errorMessage.value = "Please accept the terms and privacy policy."
            return false
        }
        _errorMessage.value = null
        return true
    }
}
