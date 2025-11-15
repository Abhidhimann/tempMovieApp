package com.abhishek.tempmovieapp.presentation.screens.moviedetails

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.tempmovieapp.core.constants.tempTag
import com.abhishek.tempmovieapp.domain.usecase.GetMovieDetailsUseCase
import com.abhishek.tempmovieapp.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[Screen.MovieDetails.ARG_MOVIE_ID])

    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> = _state.onStart {
        loadMovie(movieId)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        loadMovie(movieId)
    }

    private val _events = MutableSharedFlow<MovieDetailsEvent>()
    val events = _events.asSharedFlow()


    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            Log.i(tempTag(), "getting movies details of $movieId")
            getMovieDetailsUseCase(movieId)
                .catch { throwable ->
                    _events.emit(MovieDetailsEvent.ShowToast("Some error occurred!"))
                }
                .collect { movie ->
                    _state.update { it.copy(movie = movie) }
                }
        }
    }

}