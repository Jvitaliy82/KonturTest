package ru.jdeveloperapps.konturtest.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class UserItem(
    val biography: String,
    @Embedded
    val educationPeriod: EducationPeriod,
    val height: Double,
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val phone: String,
    val temperament: String
)