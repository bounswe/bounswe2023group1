package com.cmpe451.resq.viewmodels

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmpe451.resq.data.models.NotificationItem
import com.cmpe451.resq.data.remote.ResqService
import kotlinx.coroutines.launch

class NotificationViewModel(appContext: Context) : ViewModel() {

    private val _notificationItems by lazy { mutableStateOf<List<NotificationItem>>(listOf()) }
    val notificationItems: State<List<NotificationItem>> = _notificationItems

    init {
        fetchNotifications(appContext)
    }

    private fun fetchNotifications(appContext: Context) {
        viewModelScope.launch {
            /*
            val notifications = getNotifications(appContext)
            notifications.getOrNull()?.let {
                _notificationItems.value = it
            }
                */
            val notifications =
                listOf(
                    NotificationItem(
                        id = 1,
                        createdAt = "2021-05-01T00:00:00.000Z",
                        modifiedAt = "2021-05-01T00:00:00.000Z",
                        userId = 1,
                        title = "Notification Title",
                        body = "Notification Body",
                        relatedEntityId = 1,
                        notificationType = "Notification Type",
                        read = false
                    ),
                    NotificationItem(
                        id = 2,
                        createdAt = "2021-05-01T00:00:00.000Z",
                        modifiedAt = "2021-05-01T00:00:00.000Z",
                        userId = 1,
                        title = "Notification Title",
                        body = "Notification Body",
                        relatedEntityId = 1,
                        notificationType = "Notification Type",
                        read = false
                    ),
                )
            _notificationItems.value = notifications

        }
    }

    suspend fun getNotifications(appContext: Context): Result<List<NotificationItem>> {
        val api = ResqService(appContext)
        val response = api.getNotifications()
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.success(it)
            }
        }
        return Result.failure(Throwable(response.message()))
    }
}