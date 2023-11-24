package com.cmpe451.resq.data.models

data class ProfileData(
    var name: String?,
    var surname: String?,
    var email: String?,
    var roles: List<String>?,
    var selectedRole: String?
)


