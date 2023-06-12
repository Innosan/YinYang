package com.example.yinyang.utils

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
