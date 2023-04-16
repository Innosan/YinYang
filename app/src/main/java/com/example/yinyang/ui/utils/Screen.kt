package com.example.yinyang.ui.utils

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
    object SignUp: Screen(
        destination = SignUpDestination
    )
}
