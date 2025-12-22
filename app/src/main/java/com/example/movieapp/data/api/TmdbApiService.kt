package com.example.movieapp.data.api

import com.example.movieapp.data.model.DiscoverMovieResponse
import com.example.movieapp.data.model.GenreListResponse
import com.example.movieapp.data.model.MovieDetailResponse
import com.example.movieapp.data.model.MovieReviewResponse
import com.example.movieapp.data.model.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("genre/movie/list")
    suspend fun getGenreList(

    ): GenreListResponse

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("with_genres") genreId: Int,
        @Query("page") page: Int
    ): DiscoverMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
    ): MovieDetailResponse

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): MovieVideoResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int
    ): MovieReviewResponse
}
