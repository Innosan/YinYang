package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Favorite(
    val id: Int? = null,

    @SerialName("user_id") val userId: Int,
    @SerialName("product_id") val productId: JsonObject,
)

/**
 * Workaround for adding new favorite items
 */
data class FavoriteAdd(
    val id: Int? = null,

    @SerialName("user_id") val userId: Int,
    @SerialName("product_id") val productId: Int,
)
