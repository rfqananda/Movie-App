package com.example.movieapp.di


import com.example.movieapp.data.repository.GenreRepository
import com.example.movieapp.data.repository.MovieDetailRepository
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.repository.GenreRepositoryImpl
import com.example.movieapp.ui.repository.MovieDetailRepositoryImpl
import com.example.movieapp.ui.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGenreRepository(
        impl: GenreRepositoryImpl
    ): GenreRepository

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        impl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieDetailRepository(
        impl: MovieDetailRepositoryImpl
    ): MovieDetailRepository
}
