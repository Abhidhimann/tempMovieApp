package com.abhishek.tempmovieapp.data.mapper

import com.abhishek.tempmovieapp.core.constants.API
import com.abhishek.tempmovieapp.data.local.entity.MovieEntity
import com.abhishek.tempmovieapp.data.remote.response.MovieDto
import com.abhishek.tempmovieapp.domain.model.Movie


fun MovieDto.toEntity(): MovieEntity {
    val movieImageLink = if (posterPath == null) {
        ""
    } else API.MOVIE_IMAGE_BASE_URL.value + posterPath
    return MovieEntity(
        title = title,
        movieId = movieId,
        voteAverage = voteAverage,
        posterImg = movieImageLink,
        releaseDate = releaseDate,
        overview = overview
    )
}

fun MovieEntity.toDomain(): Movie = Movie(
    movieTitle = title,
    movieId = movieId,
    voteAverage = voteAverage,
    posterImg = posterImg,
    releaseDate = releaseDate,
    overview = overview,
    id = id
)