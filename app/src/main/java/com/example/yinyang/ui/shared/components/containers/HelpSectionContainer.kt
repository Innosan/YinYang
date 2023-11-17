package com.example.yinyang.ui.shared.components.containers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.utils.HelpSection

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HelpSectionContainer(sectionToDisplay: HelpSection) {
    SectionHeader(iconId = R.drawable.ic_home, title = sectionToDisplay.title)
    Text(text = stringResource(id = sectionToDisplay.description))

    Text(text = stringResource(id = R.string.help_options_title))
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        sectionToDisplay.availableOptions.forEach { option ->
            var showOptionDescription by remember {
                mutableStateOf(false)
            }

            ElevatedButton(onClick = {showOptionDescription = !showOptionDescription}) {
                Text(text = stringResource(id = option.title))
            }
            
            if (showOptionDescription) {
                Text(text = stringResource(id = option.description))
            }
        }
    }
}