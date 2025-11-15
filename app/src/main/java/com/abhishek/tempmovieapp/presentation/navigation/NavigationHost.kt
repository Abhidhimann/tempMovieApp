package com.abhishek.tempmovieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abhishek.tempmovieapp.presentation.screens.moviedetails.MovieDetailsScreenRoot
import com.abhishek.tempmovieapp.presentation.screens.movielist.MovieListScreenRoot

@Composable
fun NavigationHost(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination) {
        // movie list screen
        composable(Screen.MovieList.route) {
            MovieListScreenRoot() { movieId ->
                navController.navigate(Screen.MovieDetails.createRoute(movieId))
            }
        }

        // movie details screen
        composable(
            Screen.MovieDetails.route,
            arguments = listOf(navArgument(Screen.MovieDetails.ARG_MOVIE_ID) { type = NavType.LongType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getLong(Screen.MovieDetails.ARG_MOVIE_ID)
            if (movieId != null) {
                MovieDetailsScreenRoot()
            } else {
                // handle error case
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
    }
}