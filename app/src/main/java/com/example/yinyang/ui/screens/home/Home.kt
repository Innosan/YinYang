package com.example.yinyang.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.NavBar
import com.example.yinyang.ui.theme.YinYangTheme
import com.example.yinyang.R
import com.example.yinyang.ui.screens.home.components.FoodConstructor
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.components.SectionContainer

@Preview
@Composable
fun HomePage(
) {
    YinYangTheme(
        darkTheme = true,
    ) {
        ScreenContainer(
        ) {
            Column(
            ) {
                NavBar()
                SectionContainer {
                    SectionHeader(iconId = R.drawable.ic_food_constructor, title = "Do it Yourself")
                    Row(
                        modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        FoodConstructor(background = R.drawable.bg_pizza_construct, title = "Pizza")
                        FoodConstructor(background = R.drawable.bg_wok_construct, title = "Wok")
                    }
                }
            }
        }
    }
}