package com.example.yinyang.ui.shared.components.service

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.styles.buttonTextStyle

@Composable
fun NavigateToSignInUpButton(
    buttonLabel: Int,
    buttonText: Int,
    backgroundShape: RoundedCornerShape,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurfaceVariant, backgroundShape)
            .clip(backgroundShape)
            .clickable {
                onClick()
            }
            .padding(18.dp)
    ) {
        Text(
            text = stringResource(id = buttonLabel),
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = stringResource(id = buttonText) + "!",
            color = MaterialTheme.colorScheme.primary,
            style = buttonTextStyle,
            fontSize = 20.sp
        )
    }
}