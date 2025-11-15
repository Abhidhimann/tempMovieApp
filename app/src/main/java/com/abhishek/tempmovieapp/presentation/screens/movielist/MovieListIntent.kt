package com.abhishek.tempmovieapp.presentation.screens.movielist

sealed class MovieListIntent {
    data class OnSearchQueryChanged(val query: String): MovieListIntent()
    data class OnMovieClicked(val id: Int): MovieListIntent()
}