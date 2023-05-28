package com.example.yinyang.ui.screens.order.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yinyang.models.CartItem
import com.example.yinyang.ui.shared.components.ProductImage

@Composable
fun OrderCard(
    orderItem: CartItem,
    quantity: Int,
) {
    val product = orderItem.product_id

    Column() {
        Row(
            modifier = Modifier.fillMaxWidth(.95f),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            ProductImage(product = product, height = 120, isTagsVisible = false)

            Column() {
                Text(
                    text = product.title,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = "${product.weight} гр.",
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Text(
                    text = "${product.price * quantity} ₽",
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
    }
}