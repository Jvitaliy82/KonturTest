package ru.jdeveloperapps.konturtest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.jdeveloperapps.konturtest.api.UsersApi
import ru.jdeveloperapps.konturtest.db.UserDatabase
import ru.jdeveloperapps.konturtest.other.Constants
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SHARED_PREFERENCE_NAME
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideUsersApi(retrofit: Retrofit) = retrofit.create(UsersApi::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            UserDatabase::class.java,
            "users_db"
        ).build()

    @Singleton
    @Provides
    fun provideForecastDao(db: UserDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context) =
        app.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)

}