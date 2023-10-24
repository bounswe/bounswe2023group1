package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    var user: MutableState<User?> = mutableStateOf(null)
    var errorMessage: MutableState<String?> = mutableStateOf(null)

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)
            if (result.isSuccess) {
                user.value = result.getOrNull()
            } else {
                errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }
}
