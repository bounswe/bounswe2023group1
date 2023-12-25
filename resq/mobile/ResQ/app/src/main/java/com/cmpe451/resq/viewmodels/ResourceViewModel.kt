package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.manager.UserSessionManager
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.CreateResourceRequestBody
import com.cmpe451.resq.data.models.Resource
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.launch

class ResourceViewModel : ViewModel() {
    private val _selectedCategory = mutableStateOf<CategoryTreeNode?>(null)
    val selectedCategory: State<CategoryTreeNode?> = _selectedCategory

    private val _selectedType = mutableStateOf<CategoryTreeNode?>(null)
    val selectedType: State<CategoryTreeNode?> = _selectedType

    private val _selectedItem = mutableStateOf<CategoryTreeNode?>(null)
    val selectedItem: State<CategoryTreeNode?> = _selectedItem

    private val _categories = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    val categories: State<List<CategoryTreeNode>> = _categories

    private val _types = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    val types: State<List<CategoryTreeNode>> = _types

    private val _items = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    val items: State<List<CategoryTreeNode>> = _items

    private val _createResourceResponse = mutableStateOf<String?>(null)
    val createResourceResponse: State<String?> = _createResourceResponse

    private val _resourceList = mutableStateOf<List<Resource>>(emptyList())
    val resourceList: State<List<Resource>> = _resourceList

    fun updateCategory(category: CategoryTreeNode) {
        _selectedCategory.value = category
        fetchTypesForCategory(category.id)
    }

    fun updateType(type: CategoryTreeNode) {
        _selectedType.value = type
        fetchItemsForType(type.id)
    }

    fun updateItem(item: CategoryTreeNode) {
        _selectedItem.value = item
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

    fun onEnter(quantity: String, appContext: Context) {
        viewModelScope.launch {
            val result = getCreateResourceResponse(quantity, appContext)

            if (result.isSuccess) {
                _createResourceResponse.value = result.getOrNull().toString()
            } else {
                _createResourceResponse.value = result.exceptionOrNull()?.message
            }
        }
    }

    private suspend fun getCreateResourceResponse(quantity: String, appContext: Context): Result<Int> {
        val api = ResqService(appContext)
        val latitude =  UserSessionManager.getInstance(appContext).getLocation()?.latitude?: 41.086571
        val longitude =  UserSessionManager.getInstance(appContext).getLocation()?.longitude?: 29.046109
        val categoryId = _selectedItem.value?.id?.toString() ?: _selectedType.value?.id?.toString() ?: ""
        if (categoryId.isNotEmpty()) {
            val senderId = UserSessionManager.getInstance(appContext).getUserId()

            val requestBody = CreateResourceRequestBody(
                senderId = senderId,
                categoryTreeId = categoryId,
                quantity = quantity.toIntOrNull() ?: 0,
                latitude = latitude,
                longitude = longitude,
                gender = "FEMALE",
                size = null,
                status = "AVAILABLE"
            )

            val response = api.createResource(requestBody, null)
            if (response.isSuccessful) {
                response.body()?.let {
                    return Result.success(it)
                }
            }
            return Result.failure(Throwable(response.message()))
        }
        return Result.failure(Throwable(message = "No category"))
    }

    suspend fun getMyResources(userId: Long?, appContext: Context) {
        val api = ResqService(appContext)

        api.filterResourceByCategory(
            categoryTreeId = null,
            longitude = null,
            latitude = null,
            userId = userId,
            status = null,
            receiverId = null,
            onSuccess = { resourceListResponse ->
                _resourceList.value = resourceListResponse
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    fun getResourcesBySenderId(appContext: Context) {
        val api = ResqService(appContext)
        /* TODO: implement */
    }
}
