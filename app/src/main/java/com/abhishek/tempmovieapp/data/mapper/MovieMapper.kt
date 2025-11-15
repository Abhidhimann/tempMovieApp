package com.abhishek.tempmovieapp.data.mapper

import com.abhishek.tempmovieapp.data.local.entity.MovieEntity
import com.abhishek.tempmovieapp.data.remote.response.MovieDto
import com.abhishek.tempmovieapp.domain.model.Movie

fun MovieDto.toDomain(): Movie = Movie(
    movieTitle = title,
    movieId = movieId,
    rating = voteAverage,
    posterImg = posterPath ?: "",
    releaseDate = releaseDate,
    overview = overview
)

fun MovieDto.toEntity(): MovieEntity = MovieEntity(
    title = title,
    movieId = movieId,
    voteAverage = voteAverage,
    posterPath = posterPath ?: "",
    releaseDate = releaseDate,
    overview = overview
)