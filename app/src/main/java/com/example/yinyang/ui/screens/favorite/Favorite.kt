package com.example.yinyang.ui.screens.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Favorite(
    userId: Int?,
) {
    ScreenContainer {
        Text(text = "Favorite")
        Text(text = userId.toString())
    }
}