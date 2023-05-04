package com.example.yinyang.ui.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("favorite_id") val favouriteId: Int?,
    @SerialName("order_id") val orderId: Int?,
    @SerialName("cart_id") val cartId: Int?,

    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("profile_picture_url") val profilePictureUrl: String,

    val id: String,
    val rating: Int,
)