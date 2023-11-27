package com.cmpe451.resq.data.models

data class CategoryTreeNode(
    val id: Int,
    val data: String,
    val children: List<CategoryTreeNode>
)