package com.example.yinyang.ui.shared.models.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("favorite_id") val favouriteId: Int?,
    @SerialName("order_id") val orderId: Int?,
    @SerialName("cart_id") val cartId: Int?,
    @SerialName("user_uuid") val userUuid: String,

    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    @SerialName("profile_picture_url") val profilePictureUrl: String,

    val id: Int? = null,
    val rating: Int,
)