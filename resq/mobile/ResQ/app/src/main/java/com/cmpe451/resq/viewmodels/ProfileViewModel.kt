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
import com.cmpe451.resq.data.manager.UserSessionManager
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
                val data = api.getProfileInfo()
                _profileData.value = data
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(appContext: Context, profileData: ProfileData) {
        val api = ResqService(appContext)
        viewModelScope.launch {
            try {
                val response = api.updateUserData(profileData)
                if (response.isSuccessful) {
                    // Update was successful, now fetch the latest profile data
                    getUserData(appContext)
                    _updateMessage.value = response.body()
                    _errorMessage.value = null
                } else {
                    _errorMessage.value = response.message() ?: "Profile update failed. Please try again."
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unexpected error occurred."
            }
        }
    }
    fun selectRole(role: String, appContext: Context) {
        val userSessionManager: UserSessionManager = UserSessionManager.getInstance(appContext)
        val api = ResqService(appContext)
        val roles = userSessionManager.getUserRoles()
        viewModelScope.launch {
            try {
                val response = api.selectRole(role)
                Log.d("ProfileViewModel", "selectRole: $response")
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }
}