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

@Preview
@Composable
fun HomePage(
) {
    val navItems = arrayOf("Menu", "Address", "Notifications")

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
                NavBar(navItems = navItems)

                SectionHeader(icon = "Wrench", title = "Собери сам")
            }
        }
    }
}