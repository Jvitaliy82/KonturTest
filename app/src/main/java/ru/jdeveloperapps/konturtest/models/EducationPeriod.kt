package ru.jdeveloperapps.konturtest.models

import java.io.Serializable

data class EducationPeriod(
    val end: String,
    val start: String
) : Serializable