package com.example.movieapp.ui.repository

import com.example.movieapp.data.api.TmdbApiService
import com.example.movieapp.data.repository.GenreRepository
import com.example.movieapp.ui.mapper.toUiModel
import com.example.movieapp.ui.model.GenreUiModel
import com.example.registrationapp.core.Result
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : GenreRepository {

    override suspend fun getGenreList(): Result<List<GenreUiModel>> {
        return try {
            val response = apiService.getGenreList()
            val data = response.toUiModel()

            if (data.isEmpty()) {
                Result.Empty
            } else {
                Result.Success(data)
            }
        } catch (e: Exception) {
            Result.Error(e.message.orEmpty())
        }
    }
}
