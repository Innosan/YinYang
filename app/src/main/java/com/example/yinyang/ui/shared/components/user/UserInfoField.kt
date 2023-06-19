package com.example.yinyang.ui.shared.components.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserInfoField(icon: Int, fieldLabel: String, spacedBy: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacedBy.dp)
    ) {
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