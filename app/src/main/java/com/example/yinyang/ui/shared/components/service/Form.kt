package com.example.yinyang.ui.shared.components.service

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.yinyang.R
import com.example.yinyang.ui.shared.styles.buttonTextStyle
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
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(id = R.string.email_field_label))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.email_field_placeholder))
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),

                    contentDescription = "E-Mail field"
                )
            }
        )
        
        Spacer(modifier = Modifier.size(24.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password = it },
            label = {
                Text(
                    text = stringResource(id = R.string.password_field_label),
                    fontWeight = FontWeight.SemiBold
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.password_field_placeholder))
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

        Spacer(modifier = Modifier.size(40.dp))

        Button(
            onClick = {
                MainScope().launch(exceptionHandler) {
                    onFormSubmit(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
        ) {
            Text(
                text = stringResource(id = formAction).uppercase(),
                style = buttonTextStyle,
                fontSize = 24.sp
            )
        }
    }
}