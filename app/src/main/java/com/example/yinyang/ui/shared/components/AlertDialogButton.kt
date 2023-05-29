package com.example.yinyang.ui.shared.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.yinyang.ui.shared.styles.buttonTextStyle
import com.example.yinyang.utils.ButtonType

@Composable
fun AlertDialogButton(
    buttonType: ButtonType,
    onButtonClick: () -> Unit,
) {
    val buttonBackground: Color
    val fontSize: Int

    if (buttonType.lowPriority) {
        buttonBackground = MaterialTheme.colorScheme.primary.copy(.6f)
        fontSize = 14
    } else {
        buttonBackground = MaterialTheme.colorScheme.primary
        fontSize = 20
    }

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
            style = buttonTextStyle,
            fontSize = fontSize.sp,
        )
    }
}