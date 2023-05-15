package com.example.yinyang.ui.shared.components

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.yinyang.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun Form(
    formAction: Int,
    onFormSubmit: suspend (email: String, password: String) -> Unit,
) {
    val exceptionHandler = CoroutineExceptionHandler { context, error ->
        Log.d(ContentValues.TAG, error.toString())
    }

    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}

    var passwordVisible by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
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
            value = password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
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
                onFormSubmit(email, password)
            }
        }) {
            Text(text = stringResource(id = formAction))
        }
    }
}