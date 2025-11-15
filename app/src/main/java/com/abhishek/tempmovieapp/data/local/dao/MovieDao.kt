package com.abhishek.tempmovieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhishek.tempmovieapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' ORDER BY releaseDate DESC")
    fun getMovies(query: String): Flow<List<MovieEntity>>
}
