package com.abhishek.tempmovieapp.presentation.screens.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.tempmovieapp.core.constants.classTag
import com.abhishek.tempmovieapp.domain.usecase.GetTrendingMoviesUseCase
import com.abhishek.tempmovieapp.domain.usecase.RefreshTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase
) : ViewModel() {


    fun refreshMovies() {
        viewModelScope.launch {
             refreshTrendingMoviesUseCase().onSuccess {

             }.onFailure { exception ->
                 Log.e(classTag(), "Fetch movies from network $exception")
             }
        }
    }
}