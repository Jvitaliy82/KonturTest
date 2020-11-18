package ru.jdeveloperapps.konturtest.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.jdeveloperapps.konturtest.models.UserItem

@Dao
interface UserDao {

    @Query("SELECT * FROM users_table")
    fun getAllUsers(): LiveData<List<UserItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsers(users: List<UserItem>)
}