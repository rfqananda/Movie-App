package com.example.movieapp.ui.model

data class MovieDetailUiModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String,
    val backdropUrl: String,
    val runtime: String,
    val genres: String,
    val rating: Double
)
