package com.example.movieapp.ui.mapper

import com.example.movieapp.data.model.DiscoverMovieResponse
import com.example.movieapp.data.model.GenreListResponse
import com.example.movieapp.data.model.MovieDetailResponse
import com.example.movieapp.data.model.MovieReviewResponse
import com.example.movieapp.data.model.MovieVideoResponse
import com.example.movieapp.ui.model.DiscoverMovieUiModel
import com.example.movieapp.ui.model.GenreUiModel
import com.example.movieapp.ui.model.MovieDetailUiModel
import com.example.movieapp.ui.model.MovieReviewUiModel
import com.example.movieapp.ui.model.MovieVideoUiModel

fun GenreListResponse.toUiModel(): List<GenreUiModel> {
    return genres.map {
        GenreUiModel(
            id = it.id,
            name = it.name
        )
    }
}

private const val imageBaseUrl = "https://image.tmdb.org/t/p/w500"

fun DiscoverMovieResponse.toUiModel(): List<DiscoverMovieUiModel> {
    return results.map {
        DiscoverMovieUiModel(
            id = it.id,
            title = it.title,
            overview = it.overview,
            posterUrl = it.posterPath
                .takeIf { path -> path.isNotEmpty() }
                ?.let { path -> imageBaseUrl + path }
                .orEmpty(),
            rating = it.voteAverage,
            releaseDate = it.releaseDate
        )
    }
}

fun MovieDetailResponse.toUiModel(): MovieDetailUiModel {
    return MovieDetailUiModel(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterPath
            .takeIf { it.isNotEmpty() }
            ?.let { imageBaseUrl + it }
            .orEmpty(),
        backdropUrl = backdropPath
            .takeIf { it.isNotEmpty() }
            ?.let { imageBaseUrl + it }
            .orEmpty(),
        runtime = if (runtime > 0) "$runtime min" else "-",
        genres = genres.joinToString(", ") { it.name },
        rating = voteAverage
    )
}

fun MovieVideoResponse.toUiModel(): List<MovieVideoUiModel> {
    return results
        .filter { it.site == "YouTube" && it.type == "Trailer" }
        .map {
            MovieVideoUiModel(
                youtubeKey = it.key,
                title = it.name
            )
        }
}

fun MovieReviewResponse.toUiModel(): List<MovieReviewUiModel> {
    return results.map {
        MovieReviewUiModel(
            author = it.author,
            content = it.content,
            createdAt = it.createdAt
        )
    }
}
