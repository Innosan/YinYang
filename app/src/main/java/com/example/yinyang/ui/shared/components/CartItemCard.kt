package com.example.yinyang.ui.shared.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
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

@Composable
fun CartItemCard(
    cartItem: CartItem,
    quantity: Int,
    deleteCartItem: () -> Unit
) {
    val product = cartItem.product_id

    val isInCart = remember { mutableStateOf(true) }

    Column() {
        AnimatedVisibility(
            visible = isInCart.value,
            exit = slideOutVertically(
                targetOffsetY = { 260 }
            ) + fadeOut(
                targetAlpha = 0f
            )
        ) {
            Column() {
                Row(
                    modifier = Modifier.fillMaxWidth(.95f),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image_url)
                            .crossfade(true) //TODO: should change to better one
                            .build(),
                        modifier = Modifier
                            .fillMaxWidth(.45f)
                            .height(80.dp)
                            .clip(RoundedCornerShape(16.dp)),

                        contentScale = ContentScale.Crop,
                        contentDescription = "${product.title} image",
                    )

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

                ElevatedButton(onClick = {
                    isInCart.value = false

                    deleteCartItem()
                }) {
                    Text(text = "Delete from cart")
                }
            }
        }
    }
}