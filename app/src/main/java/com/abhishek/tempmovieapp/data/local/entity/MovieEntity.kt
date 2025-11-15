package com.abhishek.tempmovieapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "movies",
    indices = [Index(value = ["movieId"], unique = true)]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieId: Long,
    val title: String,
    val voteAverage: Double,
    val posterPath: String,
    val releaseDate: String,
    val overview: String
)