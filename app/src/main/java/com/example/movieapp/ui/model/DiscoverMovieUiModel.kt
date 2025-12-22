package com.example.movieapp.ui.model

data class DiscoverMovieUiModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val rating: Double,
    val releaseDate: String
)
