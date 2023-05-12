package com.example.yinyang.utils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SwipeBackground(
    dismissState: DismissState,
    dismissColor: Color,
    cornerShapeSize: Dp
) {
    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.Transparent
            DismissValue.DismissedToEnd -> Color.Transparent
            DismissValue.DismissedToStart -> dismissColor
        }
    )

    val alignment = Alignment.CenterEnd
    val icon = Icons.Default.Delete

    val iconVisibility = dismissState.targetValue == DismissValue.DismissedToStart

    Box(
        Modifier
            .fillMaxSize()
            .background(color, RoundedCornerShape(cornerShapeSize))
            .padding(horizontal = Dp(20f)),

        contentAlignment = alignment
    ) {
        AnimatedVisibility(visible = iconVisibility) {
            Icon(
                icon,
                contentDescription = "Delete Address",
            )
        }
    }
}