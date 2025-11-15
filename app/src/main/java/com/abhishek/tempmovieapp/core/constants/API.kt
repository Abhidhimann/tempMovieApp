package com.abhishek.tempmovieapp.core.constants

enum class API(val value: String) {
    BASE_URL("https://api.themoviedb.org/3/"),
    API_TIMEOUT("30"), // in secs
    MOVIE_IMAGE_BASE_URL("https://image.tmdb.org/t/p/w500")
}