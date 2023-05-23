package com.example.yinyang.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("user_uuid") val userUuid: String,

    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,

    val id: Int? = null,
    val rating: Int,
)