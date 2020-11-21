package ru.jdeveloperapps.konturtest.viewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.jdeveloperapps.konturtest.App
import ru.jdeveloperapps.konturtest.R
import ru.jdeveloperapps.konturtest.models.UserItem
import ru.jdeveloperapps.konturtest.other.Constants.Companion.KEY_LAST_DOWNLOAD
import ru.jdeveloperapps.konturtest.other.Event
import ru.jdeveloperapps.konturtest.other.Resourse
import ru.jdeveloperapps.konturtest.repositories.UsersRepository
import java.util.*

class MainViewModel @ViewModelInject constructor(
    app: Application,
    private val usersRepository: UsersRepository,
    private val sharedPreferences: SharedPreferences
): AndroidViewModel(app) {

    private val disposables = CompositeDisposable()

    val localData: LiveData<List<UserItem>> = usersRepository.getAllUsersFromDb()
    val stateData: MutableLiveData<Resourse> = MutableLiveData()
    var lastSearch: String = ""

    fun updateData() {
        if (hasInternetConnection()) {
            stateData.postValue(Resourse.Loading)
            disposables.add(
                usersRepository.getUsersFromNet()
                .subscribeOn(Schedulers.io())
                .map { listUserItem -> usersRepository.updateUsersInDb(listUserItem) }
                .subscribe({ }, { e ->
                    stateData.postValue(Resourse.Error(Event(getApplication<App>()
                        .getString(R.string.load_error))))
                }, {
                    stateData.postValue(Resourse.Success)
                    sharedPreferences.edit().putLong(KEY_LAST_DOWNLOAD, Date().time).apply()
                })
            )
        } else {
            stateData.postValue(Resourse.Error(Event(getApplication<App>()
                .getString(R.string.no_internet))))
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<App>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}