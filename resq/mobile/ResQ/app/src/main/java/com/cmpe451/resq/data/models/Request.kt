package com.cmpe451.resq.data.models

data class CategoryNode(
    val id: Int,
    val data: String,
    val children: List<CategoryNode>
)

data class CreateNeedRequestBody(
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val categoryTreeId: String,
    val quantity: Int
)