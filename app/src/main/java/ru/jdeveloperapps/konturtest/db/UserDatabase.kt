package ru.jdeveloperapps.konturtest.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.jdeveloperapps.konturtest.models.UserItem

@Database(entities = [UserItem::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}