package ru.jdeveloperapps.konturtest.viewModels

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.jdeveloperapps.konturtest.models.UserItem
import ru.jdeveloperapps.konturtest.other.Constants.Companion.KEY_LAST_DOWNLOAD
import ru.jdeveloperapps.konturtest.other.Resourse
import ru.jdeveloperapps.konturtest.repositories.UsersRepository
import java.util.*

class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val usersRepository: UsersRepository,
    private val sharedPreferences: SharedPreferences
): AndroidViewModel(app) {

    val localData: LiveData<List<UserItem>> = usersRepository.getAllUsersFromDb()
    val stateData: MutableLiveData<Resourse> = MutableLiveData()

    fun updateData() {
        stateData.postValue(Resourse.Loading())
        usersRepository.getUsersFromNet()
            .subscribeOn(Schedulers.io())
            .map { listUserItem -> usersRepository.updateUsersInDb(listUserItem) }
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listUsers ->
                Log.d("TTT", "что то работает")
            }, {e ->
                Log.d("TTT", e.message.toString())
                stateData.postValue(Resourse.Error(e.message.toString()))
            }, {
                Log.d("TTT", "Завершено")
                stateData.postValue(Resourse.Success())
                sharedPreferences.edit().putLong(KEY_LAST_DOWNLOAD, Date().time).apply()
            })
    }
}