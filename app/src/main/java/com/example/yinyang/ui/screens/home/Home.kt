package com.example.yinyang.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.yinyang.ui.screens.home.components.SectionHeader
import com.example.yinyang.ui.shared.components.NavBar
import com.example.yinyang.ui.theme.YinYangTheme
import com.example.yinyang.R

@Preview
@Composable
fun HomePage(
) {
    YinYangTheme(
        darkTheme = true,
    ) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
            ) {
                NavBar()

                SectionHeader(iconId = R.drawable.ic_food_constructor, title = "Do it Yourself")
            }
        }
    }
}