package com.example.yinyang.repository

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.yinyang.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.functions.functions
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.ktor.client.call.*
import io.ktor.http.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/**
 * Repository for handling orders and order items.
 * @param client the Supabase client used to interact with the database.
 */
class OrderRepository(
    val client: SupabaseClient,
) {
    private val gson = Gson()
    val ordersListType = object : TypeToken<List<Order>>() {}.type
    val orderItemsListType = object : TypeToken<List<OrderItem>>() {}.type

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
                status_id = 1,
            )

            val result = client.postgrest["order"]
                .insert(order).decodeSingle<OrderSerialized>()

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

    suspend fun getOrderItems(
        orderId: Int
    ) : List<OrderItem> {

        try {
            val result = client.postgrest["order_item"]
                .select(Columns.raw("*, product_id(*, category_id(title))")) {
                    OrderItem::order_id eq orderId
                }

            return gson.fromJson(result.body.toString(), orderItemsListType)
        } catch (e: Exception) {
            println(e.message)
        }

        return emptyList()
    }

//    suspend fun getPaymentToken(
//        orderId: Int,
//        orderTotalPrice: Int
//    ) : Payment {
//        val paymentInfo = client.functions.invoke(
//            function = "get-payment-token",
//            body = buildJsonObject {
//                put("url", "https://api.yookassa.ru/v3/payments")
//                put("paymentData", buildJsonObject {
//                    put("amount", buildJsonObject {
//                        put("value", orderTotalPrice)
//                        put("currency", "RUB")
//                    })
//                    put("confirmation", buildJsonObject {
//                        put("type", "embedded")
//                    })
//                    put("capture", "true")
//                    put("description", "Заказ $orderId")
//                })
//                put("storeId", "322536")
//                put("storeKey", "test_YcF4ERtODInjpJyNhCryOfR7AKTwT8gpgh1BHjX-2aw")
//                put("idempotenceKey", "$orderId-rwerewadsrRKNqweqqweqw121eqel324qwe4342")
//            },
//            headers = Headers.build {
//                append(HttpHeaders.ContentType, "application/json")
//            }
//        )
//
//        return paymentInfo.body()
//    }

    suspend fun getOrders(
        userId: Int?,
    ) : MutableState<List<Order>> {
        val orders = mutableStateOf(emptyList<Order>())

        try {
            val result = client.postgrest["order"]
                .select(Columns.raw("*, status_id(*)")) {
                    Order::user_id eq userId
                }

            orders.value = gson.fromJson(result.body.toString(), ordersListType)
        } catch (e: Exception) {
            println(e.message)
        }

        return orders
    }
}