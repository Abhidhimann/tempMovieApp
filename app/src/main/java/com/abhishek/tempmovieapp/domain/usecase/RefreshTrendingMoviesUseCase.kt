package com.abhishek.tempmovieapp.domain.usecase

import com.abhishek.tempmovieapp.domain.repository.MovieRepository

class RefreshTrendingMoviesUseCase(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshTrendingMovies()
    }
}