package com.abhishek.tempmovieapp.domain.usecase

import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(query: String): Flow<List<Movie>> {
        return repository.getMovies(query)
    }
}