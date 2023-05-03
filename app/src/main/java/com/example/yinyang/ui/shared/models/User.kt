package com.example.yinyang.ui.shared.models

import com.example.yinyang.ui.utils.client
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
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

suspend fun getUser(): User? {
    return try {
        val result = client.postgrest["user"]
            .select(
                single = true,
            ) {
                User::id eq client.gotrue.retrieveUserForCurrentSession().id
            }

        result.decodeAs<User>()
    } catch (e: Exception) {
        println(e.message)
        null
    }
}