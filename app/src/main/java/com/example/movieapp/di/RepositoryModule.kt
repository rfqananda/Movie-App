package com.example.movieapp.di


import com.example.movieapp.data.api.TmdbApiService
import com.example.movieapp.data.repository.GenreRepository
import com.example.movieapp.data.repository.MovieDetailRepository
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.repository.GenreRepositoryImpl
import com.example.movieapp.ui.repository.MovieDetailRepositoryImpl
import com.example.movieapp.ui.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Provides
    @Singleton
    fun provideGenreRepository(
        apiService: TmdbApiService
    ): GenreRepository {
        return GenreRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(
        apiService: TmdbApiService
    ): MovieRepository {
        return MovieRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        apiService: TmdbApiService
    ): MovieDetailRepository {
        return MovieDetailRepositoryImpl(apiService)
    }

}
