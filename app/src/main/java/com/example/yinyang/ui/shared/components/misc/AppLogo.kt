package com.example.yinyang.ui.shared.components.misc

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppLogo(
    appName: Int,
    logoTextSize: Int,
    bottomSpacerSize: Int
) {
    Text(
        text = stringResource(id = appName),
        fontSize = logoTextSize.sp,
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onPrimary,
    )

    Spacer(modifier = Modifier.size(bottomSpacerSize.dp))
}