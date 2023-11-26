package com.cmpe451.resq.data.models

data class CategoryNode(
    val id: Int,
    val data: String,
    val children: List<CategoryNode>
)

