package com.abhishek.tempmovieapp.presentation.screens.moviedetails


sealed class MovieDetailsEvent {
    data class ShowToast(val message: String): MovieDetailsEvent()
}