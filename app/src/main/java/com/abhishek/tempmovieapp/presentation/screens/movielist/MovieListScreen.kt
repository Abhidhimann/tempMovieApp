package com.abhishek.tempmovieapp.presentation.screens.movielist


import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abhishek.tempmovieapp.domain.model.Movie

@Composable
fun MovieListScreenRoot(viewModel: MovieListViewModel = hiltViewModel<MovieListViewModel>(), onMovieClicked: (Int) -> Unit){
    val state by viewModel.state.collectAsStateWithLifecycle()
    MovieListScreen(state, onMovieClicked)
}


@Composable
fun MovieListScreen(
    state: MovieListState,
    onMovieClicked: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
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
                    .clickable { onMovieClicked(movie.id) }
            )
        }
    }
}



@Composable
fun MovieItem(
    imageUrl: String,
    title: String,
    year: String,
    rating: Double,
    modifier : Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            )
        ) {
//            AsyncImage(
//                model = imageUrl,
//                contentDescription = "movie_image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(228.dp),
//                contentScale = ContentScale.Crop
//            )
        }

        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = colorResource(id = R.color.white),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(start = 12.dp, end = 10.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = year,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 13.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = rating.toString(),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 2.5.dp, end = 2.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.star_on),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 14.dp, height = 12.dp)
                    .padding(end = 9.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview(){
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
