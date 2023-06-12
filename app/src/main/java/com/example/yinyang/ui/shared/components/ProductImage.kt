package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.yinyang.models.Product

@Composable
fun ProductImage(
    product: Product,
    height: Int,
    isTagsVisible: Boolean,
    fraction: Float = 1f
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(height.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image_url)
                .crossfade(true) //TODO: should change to better one
                .build(),
            modifier = Modifier
                .fillMaxWidth(fraction)
                .height(height.dp)
                .clip(RoundedCornerShape(16.dp)),

            contentScale = ContentScale.Crop,
            contentDescription = "${product.title} image",
        )

        /**
         * Product tags
         */
        if (isTagsVisible) {
            Row(
                Modifier
                    .padding(18.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                if (product.count != 0) {
                    Text(
                        text = "${product.count} шт.",
                        fontWeight = FontWeight.Black,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                            .padding(7.dp),
                    )
                }

                Text(
                    text = product.category_id.title,
                    fontWeight = FontWeight.Black,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                        .padding(7.dp),
                )
            }
        }
    }
}