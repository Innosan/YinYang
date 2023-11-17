package com.example.yinyang.ui.screens.help

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.containers.BackgroundedText
import com.example.yinyang.ui.shared.components.containers.HelpSectionContainer
import com.example.yinyang.ui.shared.components.containers.ScreenContainer
import com.example.yinyang.ui.shared.components.containers.SectionHeader
import com.example.yinyang.utils.HelpSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Help() {
    ScreenContainer() {
        SectionHeader(iconId = R.drawable.ic_help, title = R.string.help_screen)
        
        Text(text = stringResource(id = R.string.help_main_note))
        BackgroundedText(textId = R.string.help_nav_note)

        HelpSectionContainer(sectionToDisplay = HelpSection.Home)
        HelpSectionContainer(sectionToDisplay = HelpSection.Profile)
    }
}