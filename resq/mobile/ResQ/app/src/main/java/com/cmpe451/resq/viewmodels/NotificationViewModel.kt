package com.cmpe451.resq.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.NotificationItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotificationViewModel : ViewModel() {

    private val _notificationItems by lazy { mutableStateOf<List<NotificationItem>>(listOf()) }
    val notificationItems: State<List<NotificationItem>> = _notificationItems


    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            val notifications = withContext(Dispatchers.IO) {
                // Simulate network call or database call to fetch notifications
                // This is where you would normally use a repository to get your data
                listOf(
                    NotificationItem(1, "Request #1", "is included by Facilitator Justin Westervelt", "9:01 am", false),
                    NotificationItem(2, "Resource #345", "arrived to Facilitator Lindsey Culhane", "9:01 am", true)
                    // Add more mock notifications or fetch from a repository
                )
            }
            _notificationItems.value = notifications
        }
    }

    // Add any additional functions that your UI may need to interact with the data
}