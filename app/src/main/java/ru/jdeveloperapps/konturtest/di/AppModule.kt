package ru.jdeveloperapps.konturtest.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import ru.jdeveloperapps.konturtest.db.UserDatabase
import ru.jdeveloperapps.konturtest.other.Constants.Companion.SHARED_PREFERENCE_NAME
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

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