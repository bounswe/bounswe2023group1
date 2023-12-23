package com.cmpe451.resq.data.models

data class Need(
    val id: Int,
    val userId: Int,
    val categoryTreeId: String,
    val description: String,
    val quantity: Int,
    val latitude: Double,
    val longitude: Double,
    val requestId: Int?,
    val status: String,
    val createdDate: String
)