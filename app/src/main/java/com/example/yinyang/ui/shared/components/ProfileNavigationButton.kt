package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import com.example.yinyang.ui.screens.destinations.DirectionDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ProfileNavigationButton(title: String, icon: Int, fraction: Float, navigator: DestinationsNavigator, destination: DirectionDestination) {
    Button(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .clip(RoundedCornerShape(10.dp)),
        onClick = { navigator.navigate(destination) }
    ) {
        Icon(
            painter = painterResource(id = icon),

            contentDescription = "Go To $title",
        )
        Text(text = title.uppercase())
    }
}