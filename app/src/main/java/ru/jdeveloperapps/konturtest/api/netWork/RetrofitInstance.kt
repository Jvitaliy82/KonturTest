package ru.jdeveloperapps.konturtest.api.netWork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.jdeveloperapps.konturtest.other.Constants.Companion.BASE_URL

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api by lazy {
            retrofit.create(UsersApi::class.java)
        }
    }
}