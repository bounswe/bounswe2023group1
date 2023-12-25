package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.Need
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.launch
import android.content.Context

class MyRequestsViewModel : ViewModel() {
    val needs = mutableStateOf<List<Need>>(emptyList())
    private val _categoryTree = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    fun getNeeds(appContext: Context) {
        val api = ResqService(appContext)
        api.viewNeedsByUserId(
            onSuccess = { needList ->
                needs.value = needList
                fetchCategoryTree(appContext)
            },
            onError = { error ->
                // Handle error
            }
        )
    }

    private fun fetchCategoryTree(context: Context) {
        val api = ResqService(context)
        viewModelScope.launch {
            val response = api.getMainCategories()
            if (response.isSuccessful) {
                _categoryTree.value = response.body() ?: emptyList()
            } else {
                // Handle error
            }
        }
    }

    fun getCategoryName(categoryId: Int): String {
        return findCategoryName(_categoryTree.value, categoryId)
    }

    private fun findCategoryName(categoryList: List<CategoryTreeNode>, categoryId: Int): String {
        for (category in categoryList) {
            if (category.id == categoryId) return category.data
            val foundName = findCategoryName(category.children, categoryId)
            if (foundName.isNotEmpty() && foundName != "Unknown Category") return foundName
        }
        return "Unknown Category"
    }
}
