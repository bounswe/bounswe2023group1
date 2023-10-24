package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.domain.RegisterUseCase
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registerUseCase: RegisterUseCase) : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    val confirmPassword = mutableStateOf("")
    val termsAccepted = mutableStateOf(false)
    val registrationMessage = mutableStateOf<String?>(null)

    fun onRegisterClicked() {
        if (password.value != confirmPassword.value) {
            registrationMessage.value = "Passwords do not match!"
            return
        }

        if (!termsAccepted.value) {
            registrationMessage.value = "Please accept the terms and privacy policy."
            return
        }

        viewModelScope.launch {
            val response = registerUseCase.execute(email.value, password.value)
            if (response.isSuccessful) {
                registrationMessage.value = response.body()?.message
            } else {
                registrationMessage.value = "Registration failed. Please try again."
            }
        }
    }
}
