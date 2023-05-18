package com.example.yinyang.models

import kotlinx.serialization.SerialName

data class Favorite(
    val id: Int? = null,

    val user_id: Int,
    val product_id: Product,
)

/**
 * Workaround for adding new favorite items
 */
data class FavoriteAdd(
    val id: Int? = null,

    @SerialName("user_id") val userId: Int,
    @SerialName("product_id") val productId: Int,
)
