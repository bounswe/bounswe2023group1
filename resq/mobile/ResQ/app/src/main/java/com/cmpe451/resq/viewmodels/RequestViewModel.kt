package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryNode
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.launch

class RequestViewModel : ViewModel() {
    private val _selectedCategory = mutableStateOf<CategoryNode?>(null)
    val selectedCategory: State<CategoryNode?> = _selectedCategory

    private val _selectedType = mutableStateOf<CategoryNode?>(null)
    val selectedType: State<CategoryNode?> = _selectedType

    private val _selectedItem = mutableStateOf<CategoryNode?>(null)
    val selectedItem: State<CategoryNode?> = _selectedItem

    private val _categories = mutableStateOf<List<CategoryNode>>(emptyList())
    val categories: State<List<CategoryNode>> = _categories

    private val _types = mutableStateOf<List<CategoryNode>>(emptyList())
    val types: State<List<CategoryNode>> = _types

    private val _items = mutableStateOf<List<CategoryNode>>(emptyList())
    val items: State<List<CategoryNode>> = _items

    private val _selectedPriority = mutableStateOf("LOW")
    val selectedPriority: State<String> = _selectedPriority

    private val _quantity = mutableStateOf<String?>(null)
    val quantity: State<String?> = _quantity

    fun updateCategory(category: CategoryNode) {
        _selectedCategory.value = category
        fetchTypesForCategory(category.id)
    }

    fun updateType(type: CategoryNode) {
        _selectedType.value = type
        fetchItemsForType(type.id)
    }


    fun updateItem(item: CategoryNode) {
        _selectedItem.value = item
    }

    fun updatePriority(priority: String) {
        _selectedPriority.value = priority
    }

    private fun fetchTypesForCategory(categoryId: Int) {
        _types.value = _categories.value.find { it.id == categoryId }?.children ?: emptyList()
        _selectedType.value = null
        _selectedItem.value = null
    }

    fun fetchItemsForType(typeId: Int) {
        val selectedCategoryChildren = _selectedCategory.value?.children ?: emptyList()
        _items.value = selectedCategoryChildren.find { it.id == typeId }?.children ?: emptyList()
        _selectedItem.value = null
    }

    fun fetchMainCategories(appContext: Context) {
        viewModelScope.launch {
            val api = ResqService(appContext)

            val response = api.getMainCategories()
            if (response.isSuccessful) {
                _categories.value = response.body() ?: emptyList()
                if (_categories.value.isNotEmpty()) {
                    _selectedCategory.value = _categories.value.first()
                }
            } else {
                // TODO: Handle error
            }
        }
    }

    fun onEnter() {
    }
}
