package com.example.movieapp.ui.repository

import com.example.movieapp.data.api.TmdbApiService
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.mapper.toUiModel
import com.example.movieapp.ui.model.DiscoverMovieUiModel
import com.example.registrationapp.core.Result
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieRepository {

    override suspend fun discoverMovies(
        genreId: Int,
        page: Int
    ): Result<List<DiscoverMovieUiModel>> {
        return try {
            val response = apiService.discoverMovies(genreId, page)
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
