package com.abhishek.tempmovieapp.data.repository

import android.util.Log
import com.abhishek.tempmovieapp.core.constants.classTag
import com.abhishek.tempmovieapp.core.utils.DataError
import com.abhishek.tempmovieapp.data.local.dao.MovieDao
import com.abhishek.tempmovieapp.data.local.prefs.MoviePrefs
import com.abhishek.tempmovieapp.data.mapper.toDomain
import com.abhishek.tempmovieapp.data.mapper.toEntity
import com.abhishek.tempmovieapp.data.remote.api.MovieApi
import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val moviePrefs: MoviePrefs,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {
    override suspend fun refreshTrendingMovies(): Result<Unit> {
        return withContext(dispatcher) {
            try {
                val nextPage = moviePrefs.currentPage + 1
                if (nextPage > moviePrefs.totalPages) {
                    return@withContext Result.failure(DataError.LimitReached)
                }
                Log.i(this@MovieRepositoryImpl.classTag(), "fetching movie with page $nextPage")
                val response = movieApi.getTrendingMovies(page = nextPage)

                if (response.isSuccessful) {
                    Log.i(this@MovieRepositoryImpl.classTag(), "got movie response ${response.body()}")
                    val moviesDto = response.body()?.results ?: emptyList()
                    // saving movies im db
                    movieDao.insertMovies(moviesDto.map { it.toEntity() })

                    moviePrefs.currentPage = response.body()?.page ?: 0
                    moviePrefs.totalPages = response.body()?.totalPages ?: moviePrefs.totalPages
                    Result.success(Unit)
                } else {
                    // can map different code to different error enums then later handle they in ui
                    Result.failure(DataError.NetworkError(response.code(), response.message()))
                }
            } catch (e: Exception) {
                Result.failure(DataError.UnknownError(e))
            }
        }
    }

    override fun getMovies(query: String): Flow<List<Movie>> {
        return movieDao.getMovies(query)
            .map { it.map { movieEntities -> movieEntities.toDomain() } }
    }
}