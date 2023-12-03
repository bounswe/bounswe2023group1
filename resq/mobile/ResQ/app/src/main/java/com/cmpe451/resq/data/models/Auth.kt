package com.cmpe451.resq.data.models

data class LoginRequestBody(
    val email: String,
    val password: String
)
data class LoginResponse(
    val jwt: String,
    val id: Int,
    val name: String,
    val surname: String,
    val email: String,
    val roles: List<String>
)
data class RegisterRequestBody(
    val name: String,
    val surname: String,
    val email: String,
    val password: String
)