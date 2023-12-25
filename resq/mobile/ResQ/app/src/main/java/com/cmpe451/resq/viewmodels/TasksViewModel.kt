package com.cmpe451.resq.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.CategoryTreeNode
import com.cmpe451.resq.data.models.Task
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    val tasks = mutableStateOf<List<Task>>(emptyList())
    private val _categoryTree = mutableStateOf<List<CategoryTreeNode>>(emptyList())
    val assigneeInfo = mutableStateOf<String>("Loading...")
    val assignerInfo = mutableStateOf<String>("Loading...")
    fun getTasks(appContext: Context) {
        val api = ResqService(appContext)
        api.getTasks(
            onSuccess = { tasklist ->
                tasks.value = tasklist
                Log.d("OngoingTasksViewModel", "tasks: $tasks")
                fetchCategoryTree(appContext)
            },
            onError = { error ->
                Log.d("OngoingTasksViewModel", "Error: $error")
            }
        )
    }

    fun viewMyTasks(appContext: Context) {
        val api = ResqService(appContext)
        api.viewMyTasks(
            onSuccess = { tasklist ->
                tasks.value = tasklist
                fetchCategoryTree(appContext)
            },
            onError = { error ->
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
        } }
    fun fetchAssigneeInfo(appContext: Context, userId: Int) {
        viewModelScope.launch {
            getUserInfoById(appContext, userId) { userInfo ->
                assigneeInfo.value = userInfo
            }
        }
    }

    fun fetchAssignerInfo(appContext: Context, userId: Int) {
        viewModelScope.launch {
            getUserInfoById(appContext, userId) { userInfo ->
                assignerInfo.value = userInfo
            }
        }
    }

    fun getUserInfoById(appContext: Context, userId: Int, callback: (String) -> Unit) {
        val api = ResqService(appContext)
        api.getUserInfo(userId) { userInfo ->
            val username = userInfo?.let { "${it.name} ${it.surname}" } ?: "Unknown"
            callback(username)
        }
    }


    // If we want to show info about resources, we might those 2 functions. Therefore not deleted.
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
