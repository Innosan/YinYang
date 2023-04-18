package com.example.yinyang.ui.screens.signin

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.yinyang.ui.shared.components.Form
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.utils.Screen
import com.example.yinyang.ui.utils.signIn
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

    ScreenContainer {
        Column {
            Text(text = "Войдите, чтобы\nпродолжить вкушать неизведанное...")

            Form(
                onFormSubmit = {email, password ->
                    signIn(email, password, context)
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
}