package com.example.yinyang.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(icon: String, title: String) {
    //TODO: implement real icons

    Row(
    ) {
        Text(text = icon)
        Text(text = title)
    }
}