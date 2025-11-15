package com.abhishek.tempmovieapp.presentation.screens.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import androidx.room.util.query
import com.abhishek.tempmovieapp.core.constants.classTag
import com.abhishek.tempmovieapp.domain.usecase.GetMoviesUseCase
import com.abhishek.tempmovieapp.domain.usecase.RefreshTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state

    init {
        observerSearchQuery()
    }

    fun onAction(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.OnSearchQueryChanged -> {
                _state.update { it.copy(searchQuery = intent.query) }
            }
            is MovieListIntent.RefreshMovies -> {
                refreshMovies()
            }
            else -> Unit
        }
    }

    fun refreshMovies() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            refreshTrendingMoviesUseCase().onSuccess {
                _state.update { it.copy(isLoading = false) }
            }.onFailure { exception ->
                Log.e(classTag(), "Fetch movies from network $exception")
                _state.update { it.copy(isLoading = false) } // todo error case do
            }
        }
    }

    fun observerSearchQuery() {
        state.map { it.searchQuery }
            .debounce(300L)
            .distinctUntilChanged()
            .flatMapLatest { query -> getMoviesUseCase(query) }
            .onEach { movies ->
                _state.update { it.copy(movies = movies) }
            }
            .launchIn(viewModelScope)
    }
}