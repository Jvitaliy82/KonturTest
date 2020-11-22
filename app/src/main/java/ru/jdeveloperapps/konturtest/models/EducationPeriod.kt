package ru.jdeveloperapps.konturtest.models

import org.joda.time.format.ISODateTimeFormat
import ru.jdeveloperapps.konturtest.other.Constants.Companion.LOCAL_DATE_FORMAT
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class EducationPeriod(
    val start: String,
    val end: String
) : Serializable {
    override fun toString(): String {
        val localFormat = SimpleDateFormat(LOCAL_DATE_FORMAT, Locale.getDefault())
        return "${localFormat.format(getDate(start))} - ${localFormat.format(getDate(end))}"
    }

    fun getDate(date: String): Date {
        val calendarFormat = ISODateTimeFormat.dateTimeNoMillis()
        val date = calendarFormat.parseDateTime(date)
        return date.toDate()
    }
}