package com.cmpe451.resq.data.remote

import com.cmpe451.resq.data.models.User


class AuthApi {

    fun login(email: String, password: String): User? {
        // dummy data
        if (email == "test" && password == "password") {
            return User(email = "test", password = "password")
        }
        return null
    }

    fun register(user: User): Boolean {
        // to-do
        return true
    }
}
