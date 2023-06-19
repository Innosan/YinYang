package com.example.yinyang.ui.screens.signin

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.network.client
import com.example.yinyang.ui.shared.components.containers.BackgroundScreenContainer
import com.example.yinyang.ui.shared.components.service.Form
import com.example.yinyang.ui.shared.components.service.NavigateToSignInUpButton
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
        BackgroundScreenContainer(background = R.drawable.bg_sign_in_up) {
            Form(
                onFormSubmit = {email, password ->
                    userActionsHandler.performUserAction(
                        userAction = UserAction.LOGIN,
                        email,
                        password,
                        null
                    )
                },

                formAction = R.string.sign_in_button,
            )
            
            Spacer(modifier = Modifier.size(20.dp))

            NavigateToSignInUpButton(
                buttonLabel = R.string.go_to_sign_up_note,
                buttonText = R.string.sign_up_button,
                backgroundShape = RoundedCornerShape(10.dp)
            ) {
                navigator.navigate(Screen.SignUp.destination)
            }
        }
    }
}