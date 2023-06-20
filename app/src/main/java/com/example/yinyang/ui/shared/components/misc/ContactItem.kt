package com.example.yinyang.ui.shared.components.misc

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.styles.buttonTextStyle

@Composable
fun ContactItem(
    text: Int,
    icon: Int,
    contentDescription: String,
    shape: RoundedCornerShape,
    contentPadding: Int = 12,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurfaceVariant, shape)
            .clip(shape)
            .clickable {
                onClick()
            }
            .padding(contentPadding.dp),

        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = contentDescription)

        Text(
            text = stringResource(id = text),
            style = buttonTextStyle,
            fontSize = 18.sp
        )
    }
}