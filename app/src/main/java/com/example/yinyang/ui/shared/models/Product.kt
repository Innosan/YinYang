package com.example.yinyang.ui.shared.models

import com.example.yinyang.ui.utils.client
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Product(
    @SerialName("created_at") val createdAt: String?,
    @SerialName("category_id") val categoryId: JsonObject,
    @SerialName("image_url") val imageUrl: String,

    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val weight: Int,
    val count: Int,
)

suspend fun getProducts(): List<Product> {
    val result = client.postgrest["product"]
        .select("*, category_id(title)")

    return result.decodeList()
}