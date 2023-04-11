package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import com.example.yinyang.ui.shared.models.Product

@Composable
fun ProductCard(product: Product) {
    Column {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = "${product.title} image",
            )
            Row() {
                Text(text = "${product.count} шт.")
                Text(text = product.categoryId.getValue("title").toString())
            }
        }
        Row() {
            Text(text = product.title)
            Text(text = "${product.weight.toString()} гр.")
        }
        Text(text = product.description)
        Row() {
            Button(onClick = { println("Bought ${product.title} for ${product.price}") }) {
                Text(text = "${product.price} ₽")
            }
            Button(onClick = { println("Added ${product.title} in favourite!") }) {
                Text(text = "Add")
            }
        }
    }
}