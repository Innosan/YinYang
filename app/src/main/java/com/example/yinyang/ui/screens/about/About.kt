package com.example.yinyang.ui.screens.about

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.ramcosta.composedestinations.annotation.Destination

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun About() {
    ScreenContainer {
        Text(text = "About")
    }
}