package com.example.yinyang.ui.screens.signin

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.yinyang.ui.shared.components.Form
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.utils.Screen
import com.example.yinyang.utils.UserAction
import com.example.yinyang.utils.UserActionsHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun SignIn(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    ScreenContainer {
        Text(text = "Войдите, чтобы\nпродолжить вкушать неизведанное...")

        Form(
            onFormSubmit = {email, password ->
                userActionsHandler.performUserAction(userAction = UserAction.LOGIN, email, password)
            },

            formAction = "Sign In",
        )

        Button(onClick = {
            navigator.navigate(Screen.SignUp.destination)
        }) {
            Text(text = "Sign Up Now!")
        }
    }
}