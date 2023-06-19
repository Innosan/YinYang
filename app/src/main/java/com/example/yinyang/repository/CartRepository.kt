package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.CartItem
import com.example.yinyang.models.CartItemAdd
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Returning

class CartRepository(val client: SupabaseClient) {
    suspend fun getCart(userId: Int?): MutableState<List<CartItem>> {
        val gson = Gson()

        val cartListType = object : TypeToken<List<CartItem>>() {}.type
        val cart = mutableStateOf(emptyList<CartItem>())

        try {
            val result = client
                .postgrest["cart_item"]
                .select(Columns.raw("*, product_id(*, category_id(title))")) {
                    if (userId != null) {
                        eq("user_id", userId)
                    }
                }

            cart.value = gson.fromJson(result.body.toString(), cartListType)
        } catch (e: Exception) {
            println(e.message)
        }

        return cart
    }

    suspend fun addToCart(userId: Int, productId: Int, quantity: Int) {
        val cartItem = CartItemAdd(
            id = null,
            productId = productId,
            userId = userId,
            quantity = quantity
        )

        try {
            client.postgrest["cart_item"]
                .insert(
                    value = cartItem,
                    returning = Returning.MINIMAL
                )
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun deleteFromCart(cartItemId: Int) {
        try {
            client.postgrest["cart_item"]
                .delete(
                    returning = Returning.MINIMAL
                ) {
                    CartItem::id eq cartItemId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }

    suspend fun deleteUserCart(userId: Int) {
        try {
            client.postgrest["cart_item"]
                .delete(
                    returning = Returning.MINIMAL
                ) {
                    CartItem::user_id eq userId
                }
        } catch (e: Exception) {
            println(e.message)
        }
    }
}