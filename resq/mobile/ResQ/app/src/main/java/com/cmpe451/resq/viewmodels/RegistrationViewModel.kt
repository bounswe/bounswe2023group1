package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.data.remote.RegisterResponse
import com.cmpe451.resq.domain.RegistrationUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel() : ViewModel() {

    private var registrationUseCase = RegistrationUseCase()

    private val _registerMessage = mutableStateOf<String?>(null)
    val registerMessage: State<String?> = _registerMessage

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        confirmPassword: String,
        termsAccepted: Boolean,
        navController: NavController
    ) {
        if (validateRegistrationInputs(name, surname, email, password, confirmPassword, termsAccepted)) {
            viewModelScope.launch {
                try {
                    val result = registrationUseCase.execute(name, surname, email, password)
                    if (result.isSuccess) {
                        _registerMessage.value = result.getOrNull()
                        _errorMessage.value = null
                        navController.navigate("login")
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
    private fun validateRegistrationInputs(name: String, surname: String, email: String, password: String, confirmPassword: String, termsAccepted: Boolean): Boolean {
        if (name.isBlank()) {
            _errorMessage.value = "Name cannot be empty."
            return false
        }
        if (surname.isBlank()) {
            _errorMessage.value = "Surname cannot be empty."
            return false
        }
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
