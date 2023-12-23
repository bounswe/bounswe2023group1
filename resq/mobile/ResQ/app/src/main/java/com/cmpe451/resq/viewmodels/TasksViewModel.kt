package com.cmpe451.resq.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TasksViewModel : ViewModel() {
    // This would be replaced the actual data source
    private val _tasks = MutableStateFlow<List<String>>(emptyList())
    val tasks: StateFlow<List<String>> = _tasks

    init {
        viewModelScope.launch {
            // TODO: Load tasks from a repository
            _tasks.value = listOf("Transport Resource #76") // Example task
        }
    }
}
