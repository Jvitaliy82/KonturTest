package ru.jdeveloperapps.konturtest.api.models

data class UserItem(
    val biography: String,
    val educationPeriod: EducationPeriod,
    val height: Double,
    val id: String,
    val name: String,
    val phone: String,
    val temperament: String
)