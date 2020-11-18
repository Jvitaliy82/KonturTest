package ru.jdeveloperapps.konturtest.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import ru.jdeveloperapps.konturtest.models.UserItem

interface UsersApi {

    @GET("generated-01.json")
    fun getList1(): Observable<List<UserItem>>

    @GET("generated-02.json")
    fun getList2(): Observable<List<UserItem>>

    @GET("generated-03.json")
    fun getList3(): Observable<List<UserItem>>

}