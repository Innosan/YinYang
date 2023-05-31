package com.example.yinyang.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(date: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())
    date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    return date.format(formatter).replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else
            it.toString()
    }
}