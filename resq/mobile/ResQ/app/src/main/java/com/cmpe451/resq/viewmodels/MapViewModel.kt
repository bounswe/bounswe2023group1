package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
    val searchQuery = mutableStateOf("")
}