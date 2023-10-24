package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private var _user by mutableStateOf(null)
    val user get() = _user

    private val _errorMessage: MutableState<String?> = mutableStateOf(null)
    val errorMessage get() = _errorMessage

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginUseCase.execute(email, password)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message
            }
        }
    }
}
