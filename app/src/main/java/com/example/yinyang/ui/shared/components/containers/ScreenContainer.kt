package com.example.yinyang.ui.shared.components.containers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenContainer(
    contentSpacing: Int = 0,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(.95f)
                .padding(horizontal = 16.dp)

        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(contentSpacing.dp)
            ) {
                content()
            }
        }
    }
}