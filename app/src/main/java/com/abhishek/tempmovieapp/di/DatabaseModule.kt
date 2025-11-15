package com.abhishek.tempmovieapp.di

import android.content.Context
import androidx.room.Room
import com.abhishek.tempmovieapp.data.local.dao.MovieDao
import com.abhishek.tempmovieapp.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: AppDataBase): MovieDao {
        return database.movieDao()
    }
}