package ru.jdeveloperapps.konturtest.models

import ru.jdeveloperapps.konturtest.other.convertDate
import java.io.Serializable

data class EducationPeriod(
    val end: String,
    val start: String
) : Serializable {
    override fun toString(): String {
        return "${convertDate(start)} - ${convertDate(end)}"
    }
}