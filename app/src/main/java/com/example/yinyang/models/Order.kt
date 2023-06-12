package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Order(
    val id: Int,

    val created_at: String?,
    val user_id: Int,
    val total_price: Int,
    val delivery_note: String?,
    val delivery_address: String?,
    val status_id: Status,
)

data class Status(
    val id: Int,
    val title: String
)

@Serializable
data class OrderAdd(
    val user_id: Int,
    val total_price: Int,
    val delivery_note: String?,
    val delivery_address: String?,
    val status_id: Int,
)

@Serializable
data class OrderItemAdd(
    val quantity: Int,

    @SerialName("order_id") val orderId: Int,
    @SerialName("product_id") val productId: Int,
)