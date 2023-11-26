package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.MutableState
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
    private val errorMessage = MutableStateFlow<String?>(null)

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

}
