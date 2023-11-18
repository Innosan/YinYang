package com.example.yinyang.ui.shared.components.service

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.yinyang.R
import com.example.yinyang.utils.ButtonType
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalTimePicker(dialogController: MutableState<Boolean>, pickedTime: MutableState<LocalTime>) {
    val timePickerState = rememberTimePickerState(
        initialHour = pickedTime.value.hour,
        initialMinute = pickedTime.value.minute,
        is24Hour = true
    )

    if (dialogController.value) {
        AlertDialog(
            onDismissRequest = {
                dialogController.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.pick_order_date_button))
            },
            text = {
                TimePicker(state = timePickerState, layoutType = TimePickerLayoutType.Vertical)
            },
            confirmButton = {
                AlertDialogButton(buttonType = ButtonType.CONFIRM) {
                    pickedTime.value = LocalTime.of(timePickerState.hour, timePickerState.minute)
                    dialogController.value = false
                }
            },
            dismissButton = {
                AlertDialogButton(buttonType = ButtonType.CLOSE) {
                    dialogController.value = false
                }
            }
        )
    }
}