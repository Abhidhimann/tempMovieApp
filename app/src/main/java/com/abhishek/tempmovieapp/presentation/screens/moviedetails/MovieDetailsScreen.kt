package com.abhishek.tempmovieapp.presentation.screens.moviedetails

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.tempmovieapp.domain.model.Movie
import com.abhishek.tempmovieapp.presentation.uiutils.MoviePoster

@Composable
fun MovieDetailsScreenRoot(
    viewModel: MovieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.movie) {
        if (state.movie == null) {
            // todo error then back
        }
    }

    state.movie?.let { movie ->
        MovieDetailsScreen(movie)
    }
}


@Composable
fun MovieDetailsScreen(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        MoviePoster(
            imageUrl = movie.posterImg,
            modifier = Modifier
                .height(400.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.movieTitle,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = movie.releaseDate,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = movie.voteAverage.toString(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Image(
                painter = painterResource(id = R.drawable.star_big_off),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp, start = 4.dp)
                    .size(14.dp),
                colorFilter = ColorFilter.tint(if (isSystemInDarkTheme()) Color.White else Color.Black)
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.overview,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Preview
@Composable
fun MovieDetailScreenPreview() {
    val movie = Movie(
        id = 1,
        movieTitle = "Inception",
        movieId = 12345,
        voteAverage = 8.8,
        posterImg = "",
        releaseDate = "2010-07-16",
        overview = "A thief enters dreams to steal secrets."
    )
    MovieDetailsScreen(movie)
}
