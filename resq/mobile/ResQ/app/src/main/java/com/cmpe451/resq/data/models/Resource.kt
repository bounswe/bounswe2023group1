package com.cmpe451.resq.data.models


data class CreateResourceRequestBody(
    var senderId: Int,
    val categoryTreeId: String,
    val quantity: Int,
    val latitude: Double,
    val longitude: Double,
    val gender: String,
    val size: String?,
    val status: String
)

data class Resource(
    var id: Int?,
    val senderId: Int,
    val receiverId: Int,
    val categoryTreeId: String,
    val gender: String,
    val quantity: Int,
    val latitude: Double,
    var longitude: Double,
    val createdDate: String,
    val size: String?,
    val file: String?,
    val status: String
)