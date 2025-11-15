package com.abhishek.tempmovieapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.abhishek.tempmovieapp.presentation.navigation.NavigationHost
import com.abhishek.tempmovieapp.presentation.navigation.Screen
import com.abhishek.tempmovieapp.presentation.theme.TempMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TempMovieAppTheme {
                val navController = rememberNavController()

                NavigationHost(
                    navController = navController,
                    startDestination = Screen.MovieList.route
                )
            }
        }
    }
}
