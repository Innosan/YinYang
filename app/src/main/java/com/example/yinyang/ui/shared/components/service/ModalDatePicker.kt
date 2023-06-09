package com.example.yinyang.ui.shared.components.service

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalDatePicker(
    dialogController: MutableState<Boolean>,
    pickedDate: MutableState<LocalDate>
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val currentDate = LocalDate.now()
                val selectedDate = Instant
                    .ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate()

                return selectedDate >=
                        currentDate && selectedDate <= currentDate.plusWeeks(2)
            }

            override fun isSelectableYear(year: Int): Boolean {
                return year == 2023
            }
        }
    )

    if (dialogController.value) {
        DatePickerDialog(
            onDismissRequest = { dialogController.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        dialogController.value = false
                        pickedDate.value = datePickerState.selectedDateMillis?.let {
                            Instant
                                .ofEpochMilli(
                                    it
                                )
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                        }!!
                    },
                ) {
                    Text("Select date")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }
}