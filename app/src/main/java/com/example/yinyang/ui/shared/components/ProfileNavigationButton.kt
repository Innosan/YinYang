package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.styles.buttonTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNavigationButton(
    title: Int,
    icon: Int,
    width: Int,
    badgeNumber: Int,
    onNavigation: () -> Unit
) {
    BadgedBox(badge = {
        Badge(
            modifier = Modifier.offset((-10).dp, 10.dp)
        ) {
            Text(
                badgeNumber.toString(),
                modifier = Modifier.semantics {
                    contentDescription = "$badgeNumber new notifications"
                },
                fontSize = 16.sp,
            )
        }
    }) {
        Button(
            modifier = Modifier
                .width(width.dp)
                .clip(RoundedCornerShape(10.dp)),

            onClick = { onNavigation() },

            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                contentColor = MaterialTheme.colorScheme.onBackground
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
}