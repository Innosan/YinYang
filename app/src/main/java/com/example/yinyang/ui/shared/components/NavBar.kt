package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.yinyang.R

@Preview
@Composable
fun NavBar() {
    val buttonModifier: Modifier = Modifier.background(color = Color.Transparent)

    val buttonColor: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = Color.White
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ElevatedButton(
            modifier = buttonModifier,
            colors = buttonColor,

            onClick = { /*TODO: implement menu opening*/ }
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = "Menu Button"
            )
        }

        Text(text = "Address Line")

        ElevatedButton(
            modifier = buttonModifier,
            colors = buttonColor,

            onClick = { /*TODO: implement notification menu opening*/ }
        )
        {
            Icon(
                painter = painterResource(id = R.drawable.ic_notification),
                contentDescription = "Notifications Button"
            )
        }
    }
}