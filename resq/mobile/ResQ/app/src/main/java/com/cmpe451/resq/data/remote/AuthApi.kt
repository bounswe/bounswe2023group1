package com.cmpe451.resq.data.remote

import com.cmpe451.resq.data.models.User


class AuthApi {

    fun login(username: String, password: String): User? {
        // dummy data
        if (username == "test" && password == "password") {
            return User(id = 1, username = "test", password = "password")
        }
        return null
    }

    fun register(user: User): Boolean {
        // to-do
        return true
    }
}
