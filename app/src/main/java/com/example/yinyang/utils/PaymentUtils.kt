package com.example.yinyang.utils

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun getPaymentBody(orderId: Int, orderTotal: Int) : JsonObject {
    return buildJsonObject {
        put("url", "https://api.yookassa.ru/v3/payments")
        put("paymentData", buildJsonObject {
            put("amount", buildJsonObject {
                put("value", orderTotal)
                put("currency", "RUB")
            })
            put("confirmation", buildJsonObject {
                put("type", "embedded")
            })
            put("capture", "true")
            put("description", "Заказ $orderId")
        })
        put("storeId", "322536")
        put("storeKey", "test_YcF4ERtODInjpJyNhCryOfR7AKTwT8gpgh1BHjX-2aw")
        put("idempotenceKey", "$orderId-rwerewadsrRKNqweqeqel324qwe4342")
    }
}