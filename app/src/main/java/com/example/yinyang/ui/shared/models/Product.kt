package com.example.yinyang.ui.shared.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Message(val text: String, @SerialName("author_id") val authorId: String, val id: Int)