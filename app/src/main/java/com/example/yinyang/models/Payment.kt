package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Payment(
    val id: String = "",
    val status: String = "",
    val paid: Boolean = false,
    val amount: Amount = Amount(),
    val confirmation: Confirmation = Confirmation(),
    @SerialName("created_at") val createdAt: String = "",
    val description: String = "",
    val metadata: JsonObject = JsonObject(emptyMap()),
    val recipient: Recipient = Recipient(),
    val refundable: Boolean = false,
    val test: Boolean = false
)

@Serializable
data class Amount(
    val value: Double = 0.0,
    val currency: String = ""
)

@Serializable
data class Confirmation(
    val type: String = "",
    @SerialName("confirmation_token") val confirmationToken: String = "",
)

@Serializable
data class Recipient(
    @SerialName("account_id") val accountId: Int = 0,
    @SerialName("gateway_id") val gatewayId: Int = 0,
)

