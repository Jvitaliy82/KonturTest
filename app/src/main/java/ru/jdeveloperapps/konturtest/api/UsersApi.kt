package ru.jdeveloperapps.konturtest.api

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import ru.jdeveloperapps.konturtest.models.ListUsers

interface UsersApi {

    @GET("generated-01.json")
    fun getList1(): Observable<ListUsers>

    @GET("generated-02.json")
    fun getList2(): Observable<ListUsers>

    @GET("generated-03.json")
    fun getList3(): Observable<ListUsers>

}