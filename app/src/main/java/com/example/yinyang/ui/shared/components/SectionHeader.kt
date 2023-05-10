package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(iconId: Int, title: String) {
    Row(
        Modifier.padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconId),

            contentDescription = title
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = title,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Black
        )
    }
}