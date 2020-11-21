package ru.jdeveloperapps.konturtest.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.jdeveloperapps.konturtest.models.UserItem

interface UsersApi {

    @GET("{source}")
    fun getContacts(@Path("source") source: String): Observable<List<UserItem>>

}