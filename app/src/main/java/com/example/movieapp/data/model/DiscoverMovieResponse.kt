package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class DiscoverMovieResponse(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("results")
    val results: List<DiscoverMovieItemResponse> = emptyList(),
    @SerializedName("total_pages")
    val totalPages: Int = 0
)

data class DiscoverMovieItemResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("vote_average")
    val voteAverage: Double = 0.0,
    @SerializedName("release_date")
    val releaseDate: String = ""
)
