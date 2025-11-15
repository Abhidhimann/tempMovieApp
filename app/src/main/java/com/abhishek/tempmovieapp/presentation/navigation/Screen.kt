package com.abhishek.tempmovieapp.presentation.navigation

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list")
    object MovieDetails : Screen("movie_details/{movieId}") {
        const val ARG_MOVIE_ID = "movieId"
        fun createRoute(movieId: Int) = "movie_details/$movieId"
    }
}