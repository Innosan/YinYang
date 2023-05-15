package com.example.yinyang.repository

import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.Favorite
import com.example.yinyang.models.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class FavoriteRepository(val client: SupabaseClient) {
    val fav = mutableStateOf<List<Favorite>>(emptyList())

    suspend fun getFavorites(userId: Int): List<Favorite> {
        try {
            val result = client.postgrest["favorite"].select("*, product_id(*)") {
                eq("user_id", userId)
            }

            fav.value = result.decodeList()
        } catch (e: Exception) {
            println(e.message)
        }

        return emptyList()
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