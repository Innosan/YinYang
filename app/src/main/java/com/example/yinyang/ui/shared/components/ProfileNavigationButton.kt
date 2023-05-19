package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.ui.shared.styles.buttonTextStyle

@Composable
fun ProfileNavigationButton(
    title: Int,
    icon: Int, fraction: Float,
    onNavigation: () -> Unit,
) {
    Button(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .clip(RoundedCornerShape(10.dp)),

        onClick = { onNavigation() },

        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White.copy(0.14f)
        ),

        shape = RoundedCornerShape(7.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = icon),

                contentDescription = "Go To $title",
            )
            Text(
                text = stringResource(id = title).uppercase(),
                style = buttonTextStyle
            )
        }
    }
}