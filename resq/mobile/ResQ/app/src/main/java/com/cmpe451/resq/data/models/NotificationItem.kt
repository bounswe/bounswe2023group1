package com.cmpe451.resq.data.models

data class NotificationItem(
    val id: Int,
    val createdAt: String,
    val modifiedAt: String,
    val userId: Int,
    val title: String,
    val body: String,
    val relatedEntityId: Int,
    val notificationType: String,
    val read: Boolean
)