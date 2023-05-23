package com.example.yinyang.ui.screens.signin

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.delay

@RootNavGraph(start = true)
@Destination
@Composable
fun SignIn(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    val userState = client.gotrue.sessionStatus.collectAsState()
    val isUserAuth = userState.value is SessionStatus.Authenticated

    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = true) {
        delay(1500)

        isLoading = false
    }

    if (isUserAuth) {
        navigator.navigate(Screen.Home.destination)
    }

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        ScreenContainer {
            Text(text = stringResource(id = R.string.sign_in_note))

            Form(
                onFormSubmit = {email, password ->
                    userActionsHandler.performUserAction(userAction = UserAction.LOGIN, email, password)
                },

                formAction = R.string.sign_in_screen,
            )

            Button(onClick = {
                navigator.navigate(Screen.SignUp.destination)
            }) {
                Text(text = stringResource(id = R.string.sign_up_screen))
            }
        }
    }
}