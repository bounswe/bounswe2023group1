package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.remote.ProfileRepository
import com.cmpe451.resq.data.models.ProfileData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel() : ViewModel() {
    private val profileRepository = ProfileRepository()
    private var _profileData: MutableState<ProfileData?> = mutableStateOf(null)
    val profile get() = _profileData
    private val errorMessage = MutableStateFlow<String?>(null)

    fun getUserData(userId: String) {
        viewModelScope.launch {
            try {
                val data = profileRepository.getUserData(userId)
                _profileData.value = data // Update the mutable state
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }

}