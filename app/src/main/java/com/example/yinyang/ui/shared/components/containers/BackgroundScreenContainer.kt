package com.example.yinyang.ui.shared.components.containers

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.misc.AppLogo

@Composable
fun BackgroundScreenContainer(
    background: Int,
    content: @Composable () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = background),
            contentDescription = "Background image",

            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .offset(y = (-60).dp),

            contentScale = ContentScale.FillWidth
        )

        Surface(
            modifier = Modifier
                .fillMaxWidth(.95f)
                .padding(horizontal = 16.dp),
            color = Color.Transparent

        ) {
            Column {
                AppLogo(
                    appName = R.string.app_name,
                    logoTextSize = 48,
                    bottomSpacerSize = 100
                )

                content()
            }
        }
    }
}