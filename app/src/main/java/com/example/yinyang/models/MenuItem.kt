package com.example.yinyang.models

import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.DirectionDestination
import com.example.yinyang.utils.Screen

data class MenuItem(
    val id: Int,
    val title: Int,
    val destination: DirectionDestination,
    val icon: Int,
)

val navItems = listOf(
    MenuItem(id = 1, title = R.string.home_screen, destination = Screen.Home.destination, icon = R.drawable.ic_home),
    MenuItem(id = 2, title = R.string.profile_screen, destination = Screen.Profile.destination, icon = R.drawable.ic_profile),
    MenuItem(id = 3, title = R.string.settings_screen, destination = Screen.Settings.destination, icon = R.drawable.ic_settings),
    MenuItem(id = 4, title = R.string.about_screen, destination = Screen.About.destination, icon = R.drawable.ic_about),
    MenuItem(id = 5, title = R.string.help_screen, destination = Screen.Help.destination, icon = R.drawable.ic_help),
)