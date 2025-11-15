package com.abhishek.tempmovieapp.domain.usecase

import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<Movie> {
        return repository.getTrendingMovies()
    }
}