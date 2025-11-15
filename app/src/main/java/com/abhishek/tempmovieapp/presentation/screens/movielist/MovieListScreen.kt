package com.abhishek.tempmovieapp.presentation.screens.movielist

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MovieListScreenRoot(viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(), onMovieClicked: (Int) -> Unit){

}
