package com.rensystem.p3_cinecode.core.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun convertMinutesToHoursMinutes(minutes: Long): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return String.format("%dh %02dmin", hours, remainingMinutes)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfMonth(dateString: String): String {
    val date = LocalDate.parse(dateString)
    return String.format("%02d", date.dayOfMonth)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAbbreviatedMonth(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH)
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getAbbreviatedMonthEs(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("MMM", Locale("es"))
    val abbreviated = date.format(formatter)
    return abbreviated.replaceFirstChar { it.uppercase() } + "."
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFullDayOfWeek(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("EEEE", Locale.ENGLISH)
    return date.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFullDayOfWeekEs(dateString: String): String {
    val date = LocalDate.parse(dateString)
    val formatter = DateTimeFormatter.ofPattern("EEEE", Locale("es"))
    val day = date.format(formatter)
    return day.replaceFirstChar { it.uppercase() }
}