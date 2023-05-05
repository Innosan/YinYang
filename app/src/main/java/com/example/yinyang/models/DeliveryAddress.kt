package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeliveryAddress(
    val id: Int? = null,
    val address: String,
    val description: String,

    @SerialName("user_id") val userId: Int
)
