package com.example.yinyang.ui.screens.constructor

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.models.ConstructorItem

@Composable
fun Constructor(
    title: String,
    description: String,
    items: List<ConstructorItem>,
) {
    ScreenContainer {
        Column {
            Text(text = title)

            Text(text = description)

            items.forEach {item ->
                Column {
                    Text(text = item.title)

                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState())
                    ) {
                        item.options.forEach {option ->
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = option.title)

                                if (option.price != 0) {
                                    Text(text = option.price.toString())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}