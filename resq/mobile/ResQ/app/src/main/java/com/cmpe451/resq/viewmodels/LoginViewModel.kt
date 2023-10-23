package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {
    var user: MutableState<User?> = mutableStateOf(null)

    fun login(email: String, password: String) {
        val loggedInUser = loginUseCase.execute(email, password)
        user.value = loggedInUser
    }
}
