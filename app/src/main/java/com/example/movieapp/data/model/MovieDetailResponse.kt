package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    val backdropPath: String = "",
    @SerializedName("runtime")
    val runtime: Int = 0,
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("genres")
    val genres: List<GenreItemResponse> = emptyList()
)
