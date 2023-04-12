package com.example.yinyang.ui.shared.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.example.yinyang.ui.shared.models.Product

@Composable
fun ProductCard(product: Product) {
    val context = LocalContext.current

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        /**
         * Product image
         */
        Box(
            Modifier
                .fillMaxWidth()
                .height(160.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),

                contentScale = ContentScale.Crop,
                contentDescription = "${product.title} image",
            )

            /**
             * Product tags
             */
            Row(
                Modifier.padding(18.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "${product.count} шт.",
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                        .padding(7.dp),
                )
                Text(
                    text = product.categoryId.getValue("title").toString(),
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .background(Color(27, 27, 27, 200), RoundedCornerShape(10.dp))
                        .padding(7.dp),
                )
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.Black,
            )
            Text(
                text = "${product.weight} гр.",
                fontWeight = FontWeight.Black,
            )
        }

        Text(text = product.description)

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick =
                {
                    Toast.makeText(
                        context,
                    "Bought ${product.title} for ${product.price}",
                        Toast.LENGTH_SHORT
                    ).show()
                },

                Modifier.fillMaxWidth(.45f)
            ) {
                Text(text = "${product.price} ₽")
            }

            Button(
                onClick =
                {
                    Toast.makeText(
                        context,
                        "Added ${product.title} in favourite!",
                        Toast.LENGTH_SHORT
                    ).show()
                },
                Modifier.fillMaxWidth(.85f)
            ) {
                Text(text = "Add")
            }
        }
    }
}