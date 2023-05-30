package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
        deliveryAddress: String,
        deliveryNote: String
    ) {
        try {
            val order = OrderAdd(
                user_id = userId,
                total_price = totalPrice,
                delivery_note = deliveryNote,
                delivery_address = deliveryAddress,
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

    suspend fun getOrder(
        orderId: Int,
        client: SupabaseClient
    ) : MutableState<Order> {
        val lastOrder = mutableStateOf(Order(1, null, 1, 1, null, null))

        try {
            lastOrder.value = client.postgrest["order"]
                .select {
                    Order::id eq orderId
                }
                .decodeSingle()
        } catch (e: Exception) {
            println(e.message)
        }

        return lastOrder
    }
}