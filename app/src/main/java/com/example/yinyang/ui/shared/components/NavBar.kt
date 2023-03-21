package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.yinyang.R

@Preview
@Composable
fun NavBar() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = "Menu Button",

            Modifier.clip(RoundedCornerShape(10.dp)).clickable {
                /* TODO: implement menu opening */
            }
        )

        Text(
            text = "Address Line",
            Modifier
                .background(Color(27, 27, 27, 200), RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .clickable {
                    /**
                    * TODO: redirect to profile/changeAddress
                    */
                }
                .padding(horizontal = 50.dp, vertical = 10.dp)
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