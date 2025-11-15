package com.abhishek.tempmovieapp.presentation.screens.movielist

import com.abhishek.tempmovieapp.domain.model.Movie

data class MovieListState (
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)