package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.Favorite
import com.example.yinyang.models.FavoriteAdd
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns


class FavoriteRepository(val client: SupabaseClient) {
    suspend fun getFavorites(userId: Int?): MutableState<List<Favorite>> {
        val gson = Gson()

        val favoriteListType = object : TypeToken<List<Favorite>>() {}.type
        val favorites = mutableStateOf(emptyList<Favorite>())

        try {
            val result = client
                .postgrest["favorite"]
                .select(Columns.raw("*, product_id(*, category_id(title))")) {
                    if (userId != null) {
                        eq("user_id", userId)
                    }
                }

            favorites.value = gson.fromJson(result.body.toString(), favoriteListType)
        } catch (e: Exception) {
            println(e.message)
        }

        return favorites
    }

    suspend fun addToFavorite(userId: Int, productId: Int) {
        val favoriteItem = FavoriteAdd(
            id = null,
            productId = productId,
            userId = userId
        )

        try {
            client.postgrest["favorite"].insert(favoriteItem)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun deleteFromFavorite(favoriteId: Int) {
        try {
            client.postgrest["favorite"]
                .delete {
                    Favorite::id eq favoriteId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}