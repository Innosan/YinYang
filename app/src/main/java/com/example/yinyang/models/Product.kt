package com.example.yinyang.models

data class Product(
    val created_at: String?,
    val category_id: Category,
    val image_url: String,

    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val weight: Int,
    val count: Int,
)

data class Category(
    val title: String,
)