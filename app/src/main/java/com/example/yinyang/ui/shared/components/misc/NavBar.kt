package com.example.yinyang.ui.shared.components.misc

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R

@Composable
fun NavBar(
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu Button",

            Modifier.clip(RoundedCornerShape(10.dp)).clickable {
            }
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_notification),
            contentDescription = "Notifications Button",

            Modifier.clip(RoundedCornerShape(10.dp)).clickable {
                /* TODO: implement notification menu opening */
            }
        )
    }
}