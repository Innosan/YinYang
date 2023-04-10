package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.ui.shared.models.Message
import coil.compose.AsyncImage

@Composable
fun ProductCard(product: Message) {
    Column {
        Box {
            AsyncImage(
                model = "https://example.com/image.jpg",
                contentDescription = null
            )
        }
    }
}