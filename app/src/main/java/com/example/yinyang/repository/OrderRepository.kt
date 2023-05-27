package com.example.yinyang.repository

import com.example.yinyang.models.*
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

/**
 * Repository for handling orders and order items.
 * @param client the Supabase client used to interact with the database.
 */
class OrderRepository(
    val client: SupabaseClient,
) {

    /**
     * Creates a new order for the given user with the specified cart items and total price.
     * @param userId the ID of the user placing the order.
     * @param cart the list of cart items representing the products being ordered.
     * @param totalPrice the total price of the order.
     */
    suspend fun createNewOrder(
        userId: Int,
        cart: List<CartItem>,
        totalPrice: Int,
    ) {
        try {
            val order = OrderAdd(
                created_at = null,
                user_id = userId,
                total_price = totalPrice,
                delivery_note = null,
                delivery_address = null
            )

            val result = client.postgrest["order"]
                .insert(order).decodeSingle<Order>()

            val orderItems = cart.map { item ->
                OrderItemAdd(
                    orderId = result.id,
                    quantity = item.quantity,
                    productId = item.product_id.id
                )
            }

            client.postgrest["order_item"]
                .insert(orderItems)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}