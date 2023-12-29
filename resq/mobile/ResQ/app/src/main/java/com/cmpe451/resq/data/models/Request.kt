package com.cmpe451.resq.data.models


data class CreateNeedRequestBody(
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val categoryTreeId: String,
    val quantity: Int
)