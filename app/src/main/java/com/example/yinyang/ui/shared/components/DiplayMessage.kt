package com.example.yinyang.ui.shared.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R

@Composable
fun DisplayMessage(
    message: Int,
    showMessage: Boolean,
    onDismiss: () -> Unit
) {
    Column() {
        AnimatedVisibility(
            visible = showMessage
        ) {
            Snackbar(
                action = {
                    Button(onClick = { onDismiss() }) {
                        Text(stringResource(id = R.string.close_button))
                    }
                }
            ) {
                Text(stringResource(id = message))
            }
        }
    }
}