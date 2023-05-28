package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.ButtonType

@Composable
fun AlertDialogButton(
    buttonType: ButtonType,
    onButtonClick: () -> Unit,
) {
    val buttonBackground =
        if (buttonType.lowPriority)
            MaterialTheme.colorScheme.primary.copy(.6f)
        else MaterialTheme.colorScheme.primary

    Button(
        onClick = {
            onButtonClick()
        },

        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonBackground
        )
    ) {
        Text(
            text = stringResource(id = buttonType.buttonText),
            style = buttonTextStyle
        )
    }
}