package com.example.yinyang.ui.shared.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BackgroundedText(textId: Int) {
    Text(
        modifier = Modifier
            .background(
                MaterialTheme.colorScheme.onSurfaceVariant,
                RoundedCornerShape(10.dp)
            )
            .padding(20.dp),

        text = stringResource(id = textId),
        fontWeight = FontWeight.ExtraBold
    )
}