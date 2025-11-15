package com.abhishek.tempmovieapp.presentation.screens.movielist

sealed class MovieListEvent {
    data class ShowToast(val message: String): MovieListEvent()
    data class ShowSnackBar(val message: String): MovieListEvent()
}