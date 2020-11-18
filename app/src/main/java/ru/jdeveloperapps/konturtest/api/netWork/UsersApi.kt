package ru.jdeveloperapps.konturtest.api.netWork

import retrofit2.Response
import retrofit2.http.GET
import ru.jdeveloperapps.konturtest.api.models.ListUsers

interface UsersApi {

    @GET("generated-01.json")
    fun getList1(): Response<ListUsers>

    @GET("generated-02.json")
    fun getList2(): Response<ListUsers>

    @GET("generated-03.json")
    fun getList3(): Response<ListUsers>

}