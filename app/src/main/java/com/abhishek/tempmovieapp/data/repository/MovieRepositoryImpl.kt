package com.abhishek.tempmovieapp.data.repository

import android.util.Log
import com.abhishek.tempmovieapp.core.constants.classTag
import com.abhishek.tempmovieapp.data.mapper.toDomain
import com.abhishek.tempmovieapp.data.remote.api.MovieApi
import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {
    override suspend fun refreshTrendingMovies(): Result<Unit> {
        return withContext(dispatcher) {
            try {
                val response = movieApi.getTrendingMovies()
                if (response.isSuccessful) {
                    val moviesDto = response.body()?.results ?: emptyList()
                    val movies = moviesDto.map { it.toDomain() }

                    // todo save it local
                    Log.i(classTag(), "Fetched movies from network $movies")
                    Result.success(Unit)
                } else {
                    // can map different code to different error enums then later handle they in ui
                    Result.failure(Exception("Network call failed: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override fun getTrendingMovies(): Flow<Movie> {
        return emptyFlow()
    }
}