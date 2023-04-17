package com.example.yinyang.ui.screens.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.yinyang.R
import com.example.yinyang.ui.shared.components.ScreenContainer
import com.example.yinyang.ui.utils.Screen
import com.example.yinyang.ui.utils.client
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.*

@Destination
@Composable
fun SignUp(
    navigator: DestinationsNavigator,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var userEmail by remember {
        mutableStateOf("")
    }
    var userPassword by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember { mutableStateOf(false) }

    suspend fun signUp() {
        try {
            client.gotrue.signUpWith(Email) {
                email = userEmail
                password = userPassword
            }

            Toast.makeText(context, "Вы успешно зарегистрировались!", Toast.LENGTH_SHORT).show()
        } catch (error: Exception) {
            Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
        }
    }

    ScreenContainer {
        Column {
            Text(text = "Зарегистрируйтесь, чтобы продолжить!")

            OutlinedTextField(
                value = userEmail,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
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
                coroutineScope.launch {
                    signUp()
                }
            }) {
                Text(text = "Sign Up")
            }

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
}