package com.example.movieapp.di

import android.content.Context
import com.example.movieapp.ui.fragments.GenreFragment
import com.example.movieapp.ui.fragments.MovieDetailFragment
import com.example.movieapp.ui.fragments.MoviesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(fragment: GenreFragment)
    fun inject(fragment: MoviesFragment)
    fun inject(fragment: MovieDetailFragment)
}
