package ru.jdeveloperapps.konturtest.repositories

import io.reactivex.rxjava3.core.Observable
import ru.jdeveloperapps.konturtest.api.UsersApi
import ru.jdeveloperapps.konturtest.db.UserDao
import ru.jdeveloperapps.konturtest.models.UserItem
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SOURCE_1
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SOURCE_2
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SOURCE_3
import javax.inject.Inject

class UsersRepository  @Inject constructor(
    private val userDao: UserDao,
    private val usersApi: UsersApi
){

    fun getUsersFromNet(): Observable<List<UserItem>> {
        return Observable.mergeDelayError(
            usersApi.getContacts(SOURCE_1),
            usersApi.getContacts(SOURCE_2),
            usersApi.getContacts(SOURCE_3)
        )
    }

    fun getAllUsersFromDb() = userDao.getAllUsers()

    fun updateUsersInDb(users: List<UserItem>) = userDao.updateUsers(users)

}