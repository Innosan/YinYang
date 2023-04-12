package com.example.yinyang.ui.shared.models

import com.example.yinyang.R

data class MenuItem(
    val id: Int,
    val title: String,
    val icon: Int,
)

val navItems = listOf(
    MenuItem(id = 1, title = "Home", icon = R.drawable.ic_home),
    MenuItem(id = 2, title = "Profile", icon = R.drawable.ic_profile),
    MenuItem(id = 3, title = "Settings", icon = R.drawable.ic_settings),
    MenuItem(id = 4, title = "Help", icon = R.drawable.ic_help),
    MenuItem(id = 5, title = "About", icon = R.drawable.ic_about),
)