package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    val registrationMessage = mutableStateOf<String?>(null)


    fun register(email: String, password: String, confirmPassword: String, termsAccepted: Boolean) {
        if (password.length < 8) {
            registrationMessage.value = "Password should be at least 8 characters long."
            return
        }

        if (password != confirmPassword) {
            registrationMessage.value = "Passwords do not match!"
            return
        }

        if (!termsAccepted) {
            registrationMessage.value = "Please accept the terms and privacy policy."
            return
        }

        viewModelScope.launch {
            try {
                val result = registerUseCase.execute(email, password)
                if (result.isSuccess) {
                    registrationMessage.value = result.getOrNull()?.message
                } else {
                    registrationMessage.value = result.exceptionOrNull()?.message ?: "Registration failed. Please try again."
                }
            } catch (e: Exception) {
                registrationMessage.value = e.message ?: "Unexpected error occurred."
            }
        }
    }
}
