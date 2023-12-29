package com.cmpe451.resq.data.models

data class ProfileData(
    var name: String?,
    var surname: String?,
    var birth_date: String?,
    var gender:String?,
    var phoneNumber: String?,
    var bloodType: String?,
    var weight: Int?,
    var height: Int?,
    var state: String?,
    var country: String?,
    val emailConfirmed: Boolean? = false,
    var city: String?,
    val privacyPolicyAccepted: Boolean? = false,
)


data class UserInfoRequest(
    var name: String?,
    var surname: String?,
    var birth_date: String?,
    var gender:String?,
    var phoneNumber: String?,
    var bloodType: String?,
    var weight: Int?,
    var height: Int?,
    var state: String?,
    var country: String?,
    val emailConfirmed: Boolean? = false,
    var city: String?,
    val privacyPolicyAccepted: Boolean? = false,
)

data class UserInfo(
    var email: String,
    var name: String,
    var surname: String,
    var roles: List<String>
)