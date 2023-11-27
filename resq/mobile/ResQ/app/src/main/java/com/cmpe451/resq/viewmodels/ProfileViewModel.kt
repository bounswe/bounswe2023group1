package com.cmpe451.resq.viewmodels

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.ProfileData
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private var _profileData: MutableState<ProfileData?> = mutableStateOf(null)
    val profile get() = _profileData
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage

    private val _updateMessage = mutableStateOf<String?>(null)
    val updateMessage: State<String?> = _updateMessage


    @RequiresApi(Build.VERSION_CODES.O)
    fun getUserData(appContext: Context) {
        val api = ResqService(appContext)

        viewModelScope.launch {
            try {
                val data = api.getUserInfo()
                Log.d("Service", "getUserData: $data")
                _profileData.value = data
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(appContext: Context, profileData: ProfileData){
        val api = ResqService(appContext)
        viewModelScope.launch {
            try {
                Log.d("Update DATA", "")
                val response = api.updateUserData(profileData)

                if (response.isSuccessful) {
                    _updateMessage.value = response.body()
                    _errorMessage.value = null
                }
                else {
                    _errorMessage.value = response.message()
                        ?: "Profile update failed. Please try again."
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unexpected error occurred."
            }
        }
    }

}
