package com.example.yinyang.ui.shared.components.misc

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.Social

@Composable
fun Socials(
    headingText: Int,
    socialsArray: List<Social>,
    contentPadding: Int = 10
) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurfaceVariant, RoundedCornerShape(10.dp))
            .padding(contentPadding.dp),

        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(id = headingText),
            color = MaterialTheme.colorScheme.onSurface,
            style = buttonTextStyle,
            fontSize = 26.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            socialsArray.forEach {
                Image(painter = painterResource(id = it.icon), contentDescription = it.title)
            }
        }
    }
}