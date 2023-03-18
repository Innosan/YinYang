package com.example.yinyang.ui.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource


@Composable
fun SectionHeader(iconId: Int, title: String) {
    //TODO: implement real icons

    Row(
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = title
        )
        Text(text = title)
    }
}