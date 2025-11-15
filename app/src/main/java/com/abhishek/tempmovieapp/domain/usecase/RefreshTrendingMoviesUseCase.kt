package com.abhishek.tempmovieapp.domain.usecase

import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import javax.inject.Inject

class RefreshTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return repository.refreshTrendingMovies()
    }
}