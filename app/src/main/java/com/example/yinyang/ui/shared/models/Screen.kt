package com.example.yinyang.ui.shared.models

import com.example.yinyang.ui.screens.destinations.*

sealed class Screen(val destination: DirectionDestination) {
    object Home: Screen(
        destination = HomePageDestination,
    )
    object Profile: Screen(
        destination = ProfileDestination
    )
    object Settings: Screen(
        destination = SettingsDestination
    )
    object Help: Screen(
        destination = HelpDestination
    )
    object About: Screen(
        destination = AboutDestination
    )
    object SignIn: Screen(
        destination = SignInDestination
    )
}
