package com.example.yinyang.utils

import com.example.yinyang.R

sealed class HelpSection(
    val title: Int,
    val description: Int,
    val availableOptions: List<SectionOptions>
) {
    object Home: HelpSection(
        title = R.string.home_screen,
        description = R.string.help_main_menu_note,
        availableOptions = listOf(
            SectionOptions.SORT,
            SectionOptions.FAVORITE,
            SectionOptions.CART
        )
    )
    object Profile: HelpSection(
        title = R.string.profile_screen,
        description = R.string.help_profile_note,
        availableOptions = listOf(
            SectionOptions.ADDRESSES,
            SectionOptions.FAVORITE
        )
    )
}