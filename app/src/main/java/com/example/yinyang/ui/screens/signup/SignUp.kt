package com.example.yinyang.ui.screens.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.containers.BackgroundScreenContainer
import com.example.yinyang.ui.shared.components.misc.Socials
import com.example.yinyang.ui.shared.components.service.Form
import com.example.yinyang.ui.shared.components.service.NavigateToSignInUpButton
import com.example.yinyang.utils.Screen
import com.example.yinyang.utils.UserAction
import com.example.yinyang.utils.UserActionsHandler
import com.example.yinyang.utils.oauthSocials
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current

    val userActionsHandler = UserActionsHandler(context)

    BackgroundScreenContainer(background = R.drawable.bg_sign_in_up) {
        Form(
            onFormSubmit = {email, password ->
                userActionsHandler.performUserAction(
                    userAction = UserAction.SIGNUP,
                    email,
                    password,
                    navigator
                )
            },

            formAction = R.string.sign_up_button,
        )

        Spacer(modifier = Modifier.size(20.dp))

        NavigateToSignInUpButton(
            buttonLabel = R.string.go_to_sign_in_note,
            buttonText = R.string.sign_in_button,
            backgroundShape = RoundedCornerShape(10.dp)
        ) {
            navigator.navigate(Screen.SignIn.destination)
        }

        Spacer(modifier = Modifier.size(20.dp))

        Socials(R.string.sign_up_with_note, socialsArray = oauthSocials, contentPadding = 12)
    }
}