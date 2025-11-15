package com.abhishek.tempmovieapp.domain.repository

import com.abhishek.tempmovieapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    // fetch movie from network
    suspend fun refreshTrendingMovies(): Result<Unit>
    // observe movies from db
    fun getMovies(query: String): Flow<List<Movie>>
}