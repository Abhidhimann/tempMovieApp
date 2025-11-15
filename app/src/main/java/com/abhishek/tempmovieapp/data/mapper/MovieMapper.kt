package com.abhishek.tempmovieapp.data.mapper

import com.abhishek.tempmovieapp.data.remote.model.MovieDto
import com.abhishek.tempmovieapp.domain.model.Movie

fun MovieDto.toDomain(): Movie = Movie(
    movieTitle = title,
    movieId = movieId,
    rating = voteAverage,
    posterImg = posterPath ?: "",
    releaseDate = releaseDate,
    overview = overview
)