package com.example.movieapp.data.repository

import com.example.movieapp.ui.model.DiscoverMovieUiModel
import com.example.registrationapp.core.Result

interface MovieRepository {
    suspend fun discoverMovies(genreId: Int, page: Int): Result<List<DiscoverMovieUiModel>>
}
