package com.abhishek.tempmovieapp.domain.usecase

import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(id: Int): Flow<Movie?> {
        return repository.getMovieById(id)
    }
}