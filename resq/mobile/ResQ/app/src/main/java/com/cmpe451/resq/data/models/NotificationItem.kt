package com.cmpe451.resq.data.models

data class NotificationItem(
    val id: Int,
    val title: String,
    val subtitle: String,
    val time: String,
    val isActionable: Boolean
)