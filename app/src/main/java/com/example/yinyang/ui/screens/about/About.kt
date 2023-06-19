package com.example.yinyang.ui.screens.about

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun About() {
    ScreenContainer(
        contentSpacing = 24
    ) {
        SectionHeader(iconId = R.drawable.ic_about, title = R.string.about_screen)

        Text(text = stringResource(id = R.string.about_us_text))
    }
}