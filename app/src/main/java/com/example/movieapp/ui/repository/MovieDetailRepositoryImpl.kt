package com.example.movieapp.ui.repository

import com.example.movieapp.data.api.TmdbApiService
import com.example.movieapp.data.repository.MovieDetailRepository
import com.example.movieapp.ui.mapper.toUiModel
import com.example.movieapp.ui.model.MovieDetailUiModel
import com.example.movieapp.ui.model.MovieReviewUiModel
import com.example.movieapp.ui.model.MovieVideoUiModel
import com.example.registrationapp.core.Result
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    private val apiService: TmdbApiService
) : MovieDetailRepository {

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailUiModel> {
        return try {
            val response = apiService.getMovieDetail(movieId)
            Result.Success(response.toUiModel())
        } catch (e: Exception) {
            Result.Error(e.message.orEmpty())
        }
    }


    override suspend fun getMovieVideos(movieId: Int): Result<List<MovieVideoUiModel>> {
        return try {
            val response = apiService.getMovieVideos(movieId)
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

    override suspend fun getMovieReviews(
        movieId: Int,
        page: Int
    ): Result<List<MovieReviewUiModel>> {
        return try {
            val response = apiService.getMovieReviews(movieId, page)
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
