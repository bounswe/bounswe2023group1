package com.cmpe451.resq.data.models

data class ProfileData(
    var name: String?,
    var surname: String?,
    var birth_date: String?,
    var gender:String?,
    var bloodType: String?,
    var phoneNumber: String?,
    var country: String?,
    var city: String?,
    var state: String?,
    var weight: Int?,
    var height: Int?,
    val emailConfirmed: Boolean? = false,
    val privacyPolicyAccepted: Boolean? = false,
)


data class UserInfoRequest(
    var name: String?,
    var surname: String?,
    var birth_date: String?,
    var gender:String?,
    var bloodType: String?,
    var phoneNumber: String?,
    var Country: String?,
    var City: String?,
    var State: String?,
    var weight: Int?,
    var height: Int?,
    val isEmailConfirmed: Boolean? = false,
    val isPrivacyPolicyAccepted: Boolean? = false,
)

data class UserInfo(
    var email: String,
    var name: String,
    var surname: String,
    var roles: List<String>
)