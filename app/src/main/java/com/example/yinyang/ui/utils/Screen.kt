package com.example.yinyang.ui.utils

import com.example.yinyang.ui.screens.destinations.*

sealed class Screen(val destination: DirectionDestination, val screenTitle: String) {
    object Home: Screen(
        destination = HomePageDestination,
        screenTitle = "Главная"
    )
    object Profile: Screen(
        destination = ProfileDestination,
        screenTitle = "Мой профиль"
    )
    object Settings: Screen(
        destination = SettingsDestination,
        screenTitle = "Настройки"
    )
    object Help: Screen(
        destination = HelpDestination,
        screenTitle = "Помощь"
    )
    object About: Screen(
        destination = AboutDestination,
        screenTitle = "О приложении"
    )
    object SignIn: Screen(
        destination = SignInDestination,
        screenTitle = "Вход"
    )
    object SignUp: Screen(
        destination = SignUpDestination,
        screenTitle = "Регистрация"
    )
}
