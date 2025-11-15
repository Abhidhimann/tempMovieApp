package com.abhishek.tempmovieapp.domain.model


data class Movie (
    val movieTitle: String,
    val movieId: Long,
    val rating: Double,
    val posterImg: String,
    val releaseDate: String,
    val overview: String,
)