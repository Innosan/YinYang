package com.example.yinyang.ui.screens.signin

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.shared.models.get
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

    val coroutineScope = rememberCoroutineScope()

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    
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

    suspend fun logOut() {
        client.gotrue.invalidateSession()
    }

    if (client.gotrue.currentAccessTokenOrNull() != null) {
        navigator.navigate(Screen.Profile.destination)
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
                    Text(text = "E-Mail")
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
                onValueChange = { userPassword = it },
                label = {
                    Text(text = "Password")
                },
                placeholder = {
                    Text(text = "Password")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_password),

                        contentDescription = "Password field"
                    )
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
                println(client.gotrue.sessionStatus.value)
            }) {
                Text(text = "Check Session status")
            }

            Button(onClick = {
                coroutineScope.launch {
                    logOut()
                }
            }) {
                Text(text = "Log out")
            }

            Button(onClick = { navigator.navigate(Screen.SignUp.destination) }) {
                Text(text = "Sign Up Now!")
            }
        }
    }
}