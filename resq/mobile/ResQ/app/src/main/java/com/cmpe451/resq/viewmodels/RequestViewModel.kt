package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RequestViewModel : ViewModel() {

    private val _selectedType = mutableStateOf<String>("Food")
    val selectedType: State<String> = _selectedType

    private val _selectedPriority = mutableStateOf<String>("Low")
    val selectedPriority: State<String> = _selectedPriority

    private val _quantity = mutableStateOf<String?>(null)
    val quantity: State<String?> = _quantity
    // Update functions
    fun updateType(type: String) {
        _selectedType.value = type
    }

    fun updatePriority(priority: String) {
        _selectedPriority.value = priority
    }

    fun updateQuantity(quantity: String) {
        _quantity.value = quantity
    }

    // Handle the enter action
    fun onEnter() {
    }
}
