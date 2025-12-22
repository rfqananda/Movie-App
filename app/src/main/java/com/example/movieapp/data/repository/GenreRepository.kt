package com.example.movieapp.data.repository

import com.example.movieapp.ui.model.GenreUiModel
import com.example.registrationapp.core.Result

interface GenreRepository {
    suspend fun getGenreList(): Result<List<GenreUiModel>>
}
