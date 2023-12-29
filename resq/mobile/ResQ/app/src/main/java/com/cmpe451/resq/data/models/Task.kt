package com.cmpe451.resq.data.models

data class Task(
    val id: Int?,
    val assignee: Int?,
    val assigner: Int?,
    val actions: List<Action>?,
    val description: String?,
    val resources: List<TaskResource>?,
    val feedbacks: List<Feedback>?,
    val urgency: String?,
    val status: String?,
    val createdDate: String?
)

data class Action(
    val id: Int?,
    val taskId: Int?,
    val verifierId: Int?,
    val description: String?,
    val startLatitude: Double?,
    val startLongitude: Double?,
    val endLatitude: Double?,
    val endLongitude: Double?,
    val dueDate: String?,
    val createdDate: String?,
    val completed: Boolean?
)

data class TaskResource(
    val id: Int?,
    val senderId: Int?,
    val receiverId: Int?,
    val categoryTreeId: String?,
    val gender: String?,
    val quantity: Int?,
    val latitude: Double?,
    val longitude: Double?,
    val createdDate: String?,
    val size: String?,
    val file: File?,
    val status: String?
)

data class File(
    val fileName: String,
    val fileUrl: String,
    val fileType: String
)

data class Feedback(
    val id: Int,
    val taskId: Int,
    val userId: Int,
    val message: String,
    val createdDate: String
)
