package com.example.yinyang.ui.screens.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Profile() {
    ScreenContainer {
        Text(text = "Profile")
    }
}