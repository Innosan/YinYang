package com.example.yinyang.ui.screens.signup

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.yinyang.ui.shared.components.Form
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.network.client
import com.example.yinyang.utils.Screen
import com.example.yinyang.utils.UserAction
import com.example.yinyang.utils.UserActionsHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue

@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    ScreenContainer {
        Text(text = "Зарегистрируйтесь, чтобы продолжить!")

        Form(
            onFormSubmit = {email, password ->
                userActionsHandler.performUserAction(userAction = UserAction.SIGNUP, email, password)
            },

            formAction = "Зарегистрироваться",
        )

        Button(onClick = {
            println(client.gotrue.sessionStatus.value)
        }) {
            Text(text = "Check Session status")
        }

        Button(onClick = {
            navigator.navigate(Screen.SignIn.destination)
        }) {
            Text(text = "Sign In, if you already have an account!")
        }
    }
}