package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ResourceViewModel : ViewModel() {

    private val _selectedType = mutableStateOf<String>("Food")
    val selectedType: State<String> = _selectedType

    private val _selectedProfesion = mutableStateOf<String>("Truck Driver")
    val selectedProfession: State<String> = _selectedProfesion

    private val _quantity = mutableStateOf<String?>(null)
    val quantity: State<String?> = _quantity
    // Update functions
    fun updateType(type: String) {
        _selectedType.value = type
    }

    fun updateProfession(profession: String) {
        _selectedProfesion.value = profession
    }

    fun updateQuantity(quantity: String) {
        _quantity.value = quantity
    }

    // Handle the enter action
    fun onEnter() {
    }
}
