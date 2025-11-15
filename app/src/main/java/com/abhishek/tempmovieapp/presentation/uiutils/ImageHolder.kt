package com.abhishek.tempmovieapp.presentation.uiutils

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abhishek.tempmovieapp.R

@Composable
fun MoviePoster(
    imageUrl: String,
    modifier: Modifier = Modifier,
    @DrawableRes defaultImageId: Int = R.drawable.movie_not_found
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        AsyncImage(
            model = imageUrl.ifEmpty { defaultImageId },
            contentDescription = "movie_image",
            modifier = modifier,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = defaultImageId),
            error = painterResource(id = defaultImageId)
        )
    }
}
