package com.example.yinyang.ui.shared.models

import com.example.yinyang.R
import com.example.yinyang.ui.screens.destinations.DirectionDestination
import com.example.yinyang.ui.utils.Screen

data class MenuItem(
    val id: Int,
    val title: String,
    val destination: DirectionDestination,
    val icon: Int,
)

val navItems = listOf(
    MenuItem(id = 1, title = "Home", destination = Screen.Home.destination, icon = R.drawable.ic_home),
    MenuItem(id = 2, title = "Profile", destination = Screen.Profile.destination, icon = R.drawable.ic_profile),
    MenuItem(id = 3, title = "Settings", destination = Screen.Settings.destination, icon = R.drawable.ic_settings),
    MenuItem(id = 4, title = "Help", destination = Screen.Help.destination, icon = R.drawable.ic_help),
    MenuItem(id = 5, title = "About", destination = Screen.About.destination, icon = R.drawable.ic_about),
    MenuItem(id = 6, title = "Sign In", destination = Screen.SignIn.destination, icon = R.drawable.ic_profile),
)