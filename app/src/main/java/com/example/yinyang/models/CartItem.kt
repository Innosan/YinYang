package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class CartItem(
    val id: Int? = null,

    val user_id: Int,
    val product_id: Product,
    val quantity: Int
)

@Serializable
data class CartItemAdd(
    val id: Int? = null,

    @SerialName("user_id") val userId: Int,
    @SerialName("product_id") val productId: Int,
    val quantity: Int,
)
