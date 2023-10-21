package com.cmpe451.resq.domain

import com.cmpe451.resq.data.models.User
import com.cmpe451.resq.data.remote.AuthApi


class LoginUseCase(private val authApi: AuthApi) {

    fun execute(username: String, password: String): User? {
        return authApi.login(username, password)
    }
}