package ru.jdeveloperapps.konturtest.viewModels

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.jdeveloperapps.konturtest.models.UserItem
import ru.jdeveloperapps.konturtest.repositories.UsersRepository

class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val usersRepository: UsersRepository
): AndroidViewModel(app) {

    val localData: LiveData<List<UserItem>> = usersRepository.getAllUsersFromDb()

    fun updateData() {

        usersRepository.getUsersFromNet()
            .subscribeOn(Schedulers.io())
            .map { listUserItem -> usersRepository.updateUsersInDb(listUserItem) }
            .subscribe({ listUsers ->
                Log.d("TTT", listUsers.toString())
            }, {e ->
                Log.d("TTT", e.message.toString())
            })
    }
}