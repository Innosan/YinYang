package com.example.yinyang.utils

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
class DateTimeFormatter {
    private val formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.getDefault())

    fun formatDate(date: LocalDate): String {

        date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        return date.format(formatter).replaceFirstChar {
            if (it.isLowerCase())
                it.titlecase(Locale.getDefault())
            else
                it.toString()
        }
    }

    fun getLocalDateTime(date: String): LocalDateTime {
        return date.toInstant().toLocalDateTime(ZoneId.of("UTC").toKotlinTimeZone())
    }

    fun formatTime(time: LocalTime): String {
        return "${time.hour}:${time.minute}"
    }

    fun formatTime(time: java.time.LocalTime): String {
        return "${time.hour}:${time.minute}"
    }
}