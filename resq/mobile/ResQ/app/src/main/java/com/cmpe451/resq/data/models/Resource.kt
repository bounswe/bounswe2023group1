package com.cmpe451.resq.data.models

data class CreateResourceRequestBody(
    var senderId: Int?,
    val categoryTreeId: String,
    val quantity: Int,
    val latitude: Double,
    val longitude: Double,
    val gender: String
)
