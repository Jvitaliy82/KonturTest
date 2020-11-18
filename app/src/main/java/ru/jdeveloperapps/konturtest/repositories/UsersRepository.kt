package ru.jdeveloperapps.konturtest.repositories

import io.reactivex.rxjava3.core.Observable
import ru.jdeveloperapps.konturtest.models.ListUsers
import ru.jdeveloperapps.konturtest.api.RetrofitInstance
import ru.jdeveloperapps.konturtest.db.UserDao
import ru.jdeveloperapps.konturtest.models.UserItem
import javax.inject.Inject

class UsersRepository  @Inject constructor(private val userDao: UserDao){

    fun getUsers(): Observable<ListUsers> {
        return RetrofitInstance.api.getList1()
//        return Observable.mergeDelayError(
//            RetrofitInstance.api.getList1(),
//            RetrofitInstance.api.getList2(),
//            RetrofitInstance.api.getList3())
    }

    fun getAllUsers() = userDao.getAllUsers()

    fun updateUsers(users: List<UserItem>) = userDao.updateUsers(users)

}