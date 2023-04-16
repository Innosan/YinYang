package com.example.yinyang.ui.screens.signup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SignUp() {
    ScreenContainer {
        Text(text = "Sign Up")
    }
}