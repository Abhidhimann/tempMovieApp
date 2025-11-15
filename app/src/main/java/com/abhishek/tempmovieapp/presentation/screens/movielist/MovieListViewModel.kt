package com.abhishek.tempmovieapp.presentation.screens.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhishek.tempmovieapp.core.constants.tempTag
import com.abhishek.tempmovieapp.core.utils.DataError
import com.abhishek.tempmovieapp.core.utils.NetworkMonitor
import com.abhishek.tempmovieapp.domain.usecase.GetMoviesUseCase
import com.abhishek.tempmovieapp.domain.usecase.RefreshTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val refreshTrendingMoviesUseCase: RefreshTrendingMoviesUseCase,
    networkMonitor: NetworkMonitor,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<MovieListEvent>()
    val events = _events.asSharedFlow()

    val isConnected = networkMonitor.isConnectedFlow
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
        _state.update { it.copy(isInitialLoading = true) }
        observerSearchQuery()
    }

    fun onAction(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.OnSearchQueryChanged -> {
                _state.update { it.copy(searchQuery = intent.query) }
            }

            is MovieListIntent.RefreshMovies -> {
                viewModelScope.launch {
                    refreshMovies()
                }
            }

            else -> Unit
        }
    }

    suspend fun refreshMovies() {
        _state.update { it.copy(isLoading = true) }
        Log.i(tempTag(), "network is ${isConnected.value}")
        if (!isConnected.value) {
            _events.emit(MovieListEvent.ShowSnackBar("No Internet Connection"))
            _state.update { it.copy(isLoading = false) }
            return
        }

        refreshTrendingMoviesUseCase().onSuccess {
            _state.update { it.copy(isLoading = false) }
        }.onFailure { exception ->
            _state.update { it.copy(isLoading = false) }
            Log.e(tempTag(), "Fetch movies from network $exception")

            // if move used then make a error handler fun
            val message = when (exception) {
                is DataError.LimitReached -> "No more movies found"
                is DataError.NetworkError -> "Network error occurred!"
                else -> "Some error occurred!"
            }

            _events.emit(MovieListEvent.ShowToast(message))

        }
    }

    fun observerSearchQuery() {
        state.map { it.searchQuery }
            .debounce(300L)
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getMoviesUseCase(query)
            }
            .onEach { movies ->
                if (state.value.searchQuery.isEmpty() && movies.isEmpty()) {
                    // app launch first time
                    refreshMovies()
                    _state.update { it.copy(isInitialLoading = false) }
                } else if (state.value.searchQuery.isEmpty() && !movies.isEmpty()) {
                    // cold start
                    _state.update { it.copy(isInitialLoading = false, movies = movies) }
                } else {
                    _state.update { it.copy(movies = movies) }
                }
            }
            .launchIn(viewModelScope)
    }
}