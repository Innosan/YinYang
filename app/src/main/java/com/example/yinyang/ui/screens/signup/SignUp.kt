package com.example.yinyang.ui.screens.signup

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R
import com.example.yinyang.network.client
import com.example.yinyang.ui.shared.components.Form
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.utils.Screen
import com.example.yinyang.utils.UserAction
import com.example.yinyang.utils.UserActionsHandler
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue

@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    ScreenContainer {
        Text(text = stringResource(id = R.string.sign_up_note))

        Form(
            onFormSubmit = {email, password ->
                userActionsHandler.performUserAction(userAction = UserAction.SIGNUP, email, password, navigator)
            },

            formAction = R.string.sign_up_screen,
        )

        Button(onClick = {
            navigator.navigate(Screen.SignIn.destination)
        }) {
            Text(text = stringResource(id = R.string.sign_in_screen))
        }
    }
}