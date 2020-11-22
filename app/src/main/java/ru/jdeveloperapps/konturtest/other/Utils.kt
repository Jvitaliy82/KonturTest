package ru.jdeveloperapps.konturtest.other

import ru.jdeveloperapps.konturtest.other.Constants.Companion.LOCAL_DATE_FORMAT
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SERVER_DATE_FORMAT
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun convertDate(date: String): String {
    val calendarFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
    val calendar = Calendar.getInstance()
    try {
        calendar.time = calendarFormat.parse(date)!!
    } catch (e: ParseException) {
        return "bad date format"
    }
    val dateFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(calendar.time)
}

fun getDate(date: String) {
    val calendarFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.getDefault())
}