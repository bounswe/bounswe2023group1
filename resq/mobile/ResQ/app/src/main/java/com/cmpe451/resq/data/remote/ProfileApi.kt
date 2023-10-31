package com.cmpe451.resq.data.remote

import com.cmpe451.resq.data.models.ProfileData

class ProfileRepository {
    fun getUserData(userId: Int): ProfileData {
        //(TODO (make an actual API request here))
        // This is a placeholder response
/*
        return ProfileData(
            name = "Responder",
            surname = "Responderoğlu",
            dateOfBirth = "01/01/1990",
            role = "Responder" ,
            address = "123 Main Street, apt 4B San Diego CA, 91911"
            //address = null
        )
*/


        return ProfileData(
            name = "Ahmet",
            surname = "Demooğlu",
            dateOfBirth = "01/10/2000",
            role = "Victim" ,
            //address = "123 Main Street, apt 4B San Diego CA, 91911"
            address = null
        )
    }
}