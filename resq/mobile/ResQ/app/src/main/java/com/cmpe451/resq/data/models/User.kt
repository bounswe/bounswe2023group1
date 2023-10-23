package com.cmpe451.resq.data.models


data class User(
    val email: String,
    val password: String
    // Add other fields if required
)

object DummyUserData {
    val users = mutableListOf<User>(
        User(email = "dummy1@example.com", password = "password1"),
        User(email = "dummy2@example.com", password = "password2")
    )
}