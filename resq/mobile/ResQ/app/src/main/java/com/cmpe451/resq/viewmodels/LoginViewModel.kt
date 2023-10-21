package com.cmpe451.resq.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.domain.LoginUseCase

class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    val user: MutableLiveData<User?> = MutableLiveData()

    fun login(username: String, password: String) {
        val loggedInUser = loginUseCase.execute(username, password)
        user.value = loggedInUser
    }
}
