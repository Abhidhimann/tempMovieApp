package com.abhishek.tempmovieapp.presentation.screens.movielist


import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.presentation.uiutils.MoviePoster

@Composable
fun MovieListScreenRoot(
    viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(),
    onMovieClicked: (Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieListScreen(state) { intent ->
        if (intent is MovieListIntent.OnMovieClicked) {
            onMovieClicked.invoke(intent.id)
        } else viewModel.onAction(intent)
    }
}


@Composable
fun MovieListScreen(
    state: MovieListState,
    onAction: (MovieListIntent) -> Unit
) {
    var searchQuery by remember { mutableStateOf(state.searchQuery) }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            MovieListTopBar(
                query = searchQuery,
                onQueryChanged = {
                    searchQuery = it
                    onAction(MovieListIntent.OnSearchQueryChanged(it))
                }
            )
        },
        modifier = Modifier
            .fillMaxSize()
    ) { paddingValues ->
        MovieListContent(
            state, Modifier
                .fillMaxSize()
                .padding(paddingValues), onAction
        )
    }
}


@Composable
fun MovieListTopBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChanged(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = WindowInsets.systemBars.asPaddingValues().calculateTopPadding(),
            ),
        placeholder = { Text("Search movies...") },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_menu_search),
                contentDescription = "Search icon"
            )
        },
    )
}


@Composable
fun MovieListContent(
    state: MovieListState,
    modifier: Modifier,
    onAction: (MovieListIntent) -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = state.isLoading,
        onRefresh = {
            onAction.invoke(MovieListIntent.RefreshMovies)
        },
        state = pullRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isLoading,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                state = pullRefreshState
            )
        },
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier,
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(state.movies) { movie ->
                MovieItem(
                    imageUrl = movie.posterImg,
                    title = movie.movieTitle,
                    year = movie.releaseDate,
                    rating = movie.voteAverage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onAction(MovieListIntent.OnMovieClicked(movie.id)) }
                )
            }
        }
    }
}


@Composable
fun MovieItem(
    imageUrl: String,
    title: String,
    year: String,
    rating: Double,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        MoviePoster(
            imageUrl, modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        )

        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 10.dp, end = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = year,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = rating.toString(),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 2.5.dp, end = 2.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.star_big_off),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(14.dp),
                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
            )
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    val tempMovies = listOf(
        Movie(
            id = 1,
            movieTitle = "Inception",
            movieId = 12345,
            voteAverage = 8.8,
            posterImg = "",
            releaseDate = "2010-07-16",
            overview = "A thief enters dreams to steal secrets."
        ),
        Movie(
            id = 2,
            movieTitle = "Interstellar",
            movieId = 12346,
            voteAverage = 8.6,
            posterImg = "",
            releaseDate = "2014-11-07",
            overview = "A team travels through a wormhole to save humanity."
        )
    )
    val state = MovieListState(movies = tempMovies)
    MovieListScreen(state) { }
}
