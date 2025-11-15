package com.abhishek.tempmovieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abhishek.tempmovieapp.data.local.dao.MovieDao
import com.abhishek.tempmovieapp.data.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}