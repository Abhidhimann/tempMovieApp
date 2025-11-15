package com.abhishek.tempmovieapp.domain.model


data class Movie (
    val movieTitle: String,
    val movieId: Long,
    val voteAverage: Double,
    val posterImg: String,
    val releaseDate: String,
    val overview: String,
)