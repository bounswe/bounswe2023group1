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
                _profileData.value = data
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getupdateProfileResponse(profileData: ProfileData, appContext: Context): Result<String> {
        val api = ResqService(appContext)

        try {
            val response = api.updateUserData(profileData)

            // Log response details
            Log.d("Network", "Response Code: ${response.code()}")
            Log.d("Network", "Response Body: ${response.body()?.string()}")


            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.success(it.string())
                }
            } else {
                // Log error details
                Log.e("Network", "Error Message: ${response.message()}")
            }
        } catch (e: Exception) {
            // Log exception details
            Log.e("Network", "Exception: ${e.message}", e)
        }

        return Result.failure(Throwable("Unexpected error occurred"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateProfile(appContext: Context, profileData: ProfileData){

        viewModelScope.launch {
            try {
                val result = getupdateProfileResponse(profileData, appContext)
                if (result.isSuccess) {
                    _updateMessage.value = result.getOrNull()
                    _errorMessage.value = null
                }
                else {
                    _errorMessage.value = result.exceptionOrNull()?.message
                        ?: "Profile update failed. Please try again."
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Unexpected error occurred."
            }
        }
    }

}
