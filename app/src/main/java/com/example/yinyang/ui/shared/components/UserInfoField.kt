package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun UserInfoFiled(icon: Int, fieldLabel: String) {
    Row() {
        Icon(
            painter = painterResource(id = icon),

            contentDescription = fieldLabel
        )

        Text(
            text = fieldLabel,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}