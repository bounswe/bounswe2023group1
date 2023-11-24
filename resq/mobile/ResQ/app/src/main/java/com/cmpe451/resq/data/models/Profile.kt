package com.cmpe451.resq.data.models

data class ProfileData(
    var name: String?,
    var surname: String?,
    var email: String?,
    var roles: List<String>?,
    var selectedRole: String?,
    var phoneNumber: String?,
    var country: String?,
    var city: String?,
    var state: String?,
    var bloodType: String?,
    var weight: String?,
    var gender:String?,
    var height: String?,
    var year: String?,
    var month: String?,
    var day: String?,
)
