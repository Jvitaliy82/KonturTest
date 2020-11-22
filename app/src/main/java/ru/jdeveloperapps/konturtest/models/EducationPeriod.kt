package ru.jdeveloperapps.konturtest.models

import java.io.Serializable

data class EducationPeriod(
    val end: String,
    val start: String
) : Serializable {
    override fun toString(): String {
        val dateStart = start.substringBefore('T')
        val dateEnd = end.substringBefore('T')
        return "$dateStart - $dateEnd"
    }
}