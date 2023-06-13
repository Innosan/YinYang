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

@Serializable
data class OrderSerialized(
    val id: Int,

    @SerialName("created_at") val createdAt: String?,
    @SerialName("user_id") val userId: Int,
    @SerialName("total_price") val totalPrice: Int,
    @SerialName("delivery_note") val deliveryNote: String?,
    @SerialName("delivery_address") val deliveryAddress: String?,
    @SerialName("status_id") val statusId: Int,
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

data class OrderItem(
    val quantity: Int,

    val order_id: Int,
    val product_id: Product,
)