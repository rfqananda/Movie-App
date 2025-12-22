package com.example.movieapp.data.repository

import com.example.movieapp.ui.model.MovieDetailUiModel
import com.example.movieapp.ui.model.MovieReviewUiModel
import com.example.movieapp.ui.model.MovieVideoUiModel
import com.example.registrationapp.core.Result

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetailUiModel>
    suspend fun getMovieVideos(movieId: Int): Result<List<MovieVideoUiModel>>
    suspend fun getMovieReviews(movieId: Int, page: Int): Result<List<MovieReviewUiModel>>
}
