package com.example.yinyang.ui.screens.signin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.utils.Screen
import com.example.yinyang.ui.utils.client
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.*

@RootNavGraph(start = true)
@Destination
@Composable
fun SignIn(
    navigator: DestinationsNavigator,
) {
    val exceptionHandler = CoroutineExceptionHandler { context, error ->
        // Do what you want with the error
        Log.d(TAG, error.toString())
    }

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    
    var logInError by remember { mutableStateOf(Exception())}

    suspend fun logIn() = withContext(Dispatchers.IO) {
        try {
            client.gotrue.loginWith(Email) {
                email = userEmail
                password = userPassword
            }
        } catch (error: Exception) {
            logInError = error
        }
    }

    ScreenContainer {
        Column {
            Text(text = "Войдите, чтобы\nпродолжить вкушать неизведанное...")

            Text(text = logInError.message.toString())

            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it },
                label = {
                    Text(text = "E-Mail")
                },
                placeholder = {
                    Text(text = "Type in your e-mail...")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),

                        contentDescription = "E-Mail field"
                    )
                }
            )

            OutlinedTextField(
                value = userPassword,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { userPassword = it },
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Type in your password...")
                },
                visualTransformation =
                    if (passwordVisible)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password),

                        contentDescription = "Password field"
                    )
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.VisibilityOff
                    else Icons.Filled.Visibility

                    IconButton(
                        onClick = {passwordVisible = !passwordVisible},
                    ) {
                        Icon(
                            imageVector = image,

                            contentDescription = "Password field"
                        )
                    }
                }

            )

            Button(onClick = {
                MainScope().launch(exceptionHandler) {
                    logIn()
                }
            }) {
                Text(text = "Sign In")
            }

            Button(onClick = {
                navigator.navigate(Screen.SignUp.destination)
            }) {
                Text(text = "Sign Up Now!")
            }
        }
    }
}