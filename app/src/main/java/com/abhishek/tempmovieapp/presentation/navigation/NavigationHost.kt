package com.abhishek.tempmovieapp.presentation.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abhishek.tempmovieapp.core.constants.tempTag
import com.abhishek.tempmovieapp.presentation.screens.moviedetails.MovieDetailsScreenRoot
import com.abhishek.tempmovieapp.presentation.screens.movielist.MovieListScreenRoot

@Composable
fun NavigationHost(navController: NavHostController, startDestination: String) {
    NavHost(navController, startDestination,
        exitTransition = {
            fadeOut(animationSpec = tween(durationMillis = 200))
        },
        enterTransition = {
            fadeIn(animationSpec = tween(durationMillis = 200))
        },
        ) {
        // movie list screen
        composable(Screen.MovieList.route) {
            MovieListScreenRoot() { movieId ->
                Log.i(tempTag(), "navigating to movie $movieId")
                navController.navigate(Screen.MovieDetails.createRoute(movieId))
            }
        }

        // movie details screen
        composable(
            Screen.MovieDetails.route,
            arguments = listOf(navArgument(Screen.MovieDetails.ARG_MOVIE_ID) { type = NavType.IntType})
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt(Screen.MovieDetails.ARG_MOVIE_ID)
            if (movieId != null) {
                MovieDetailsScreenRoot() {
                    Log.i(tempTag(), "on back called")
                    navController.popBackStack()
                }
            } else {
                Log.i(tempTag(), "movie id is null")
                // handle error case
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }
    }
}