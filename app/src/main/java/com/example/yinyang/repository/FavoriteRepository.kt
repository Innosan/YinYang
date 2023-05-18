package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.Favorite
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class FavoriteRepository(val client: SupabaseClient) {
    suspend fun getFavorites(userId: Int?): MutableState<List<Favorite>> {
        val favorites = mutableStateOf(emptyList<Favorite>())

        try {
            val result = client.postgrest["favorite"].select("*, product_id(*)") {
                if (userId != null) {
                    eq("user_id", userId)
                }
            }

            favorites.value = result.decodeList()
        } catch (e: Exception) {
            println(e.message)
        }

        return favorites
    }

//    suspend fun addToFavorite(userId: Int, productId: Int) {
//        val favoriteItem = Favorite(
//            id = null,
//            productId = productId,
//            userId = userId
//        )
//
//        try {
//            client.postgrest["favorite"].insert(favoriteItem)
//        } catch (e: Exception) {
//            println(e.message)
//        }
//    }
}