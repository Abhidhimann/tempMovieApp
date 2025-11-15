package com.abhishek.tempmovieapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieDto>
)

data class MovieDto(
    @SerializedName("title") val title: String,
    @SerializedName("id") val movieId: Long,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("overview") val overview: String
)