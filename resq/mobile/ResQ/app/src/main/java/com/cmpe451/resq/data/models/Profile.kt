package com.cmpe451.resq.data.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
    val emailConfirmed: Boolean? = false,
    val privacyPolicyAccepted: Boolean? = false,
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedBirthDate(): String? {
        val year = year?.toIntOrNull()
        val month = month?.toIntOrNull()
        val day = day?.toIntOrNull()

        if (year != null && month != null && day != null) {
            val localDate = LocalDate.of(year, month, day)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return localDate.format(formatter)
        }

        return null
    }
}

data class UserInfo(
    val name: String,
    val surname: String,
    val email: String,
    val roles: List<String>,
    val birth_date: String?,
    val country: String?,
    val city: String,
    val state: String?,
    val bloodType: String?,
    val height: Int?,
    val weight: Int?,
    val gender: String?,
    val phoneNumber: String,
    val emailConfirmed: Boolean = true,
    val privacyPolicyAccepted: Boolean = false,
)

data class UserInfoRequest(
    val name: String,
    val surname: String,
    val email: String,
    val roles: List<String>,
    val birth_date: String?,
    val country: String?,
    val city: String,
    val state: String?,
    val bloodType: String?,
    val height: Int?,
    val weight: Int?,
    val gender: String?,
    val phoneNumber: String,

)

