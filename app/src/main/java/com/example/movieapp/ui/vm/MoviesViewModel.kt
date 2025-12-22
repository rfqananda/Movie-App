package com.example.movieapp.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.model.DiscoverMovieUiModel
import javax.inject.Inject
import com.example.registrationapp.core.Result
import kotlinx.coroutines.launch

class MoviesViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<Result<List<DiscoverMovieUiModel>>>()
    val movies: LiveData<Result<List<DiscoverMovieUiModel>>> = _movies

    private val _loadMoreMovies = MutableLiveData<Result<List<DiscoverMovieUiModel>>>()
    val loadMoreMovies: LiveData<Result<List<DiscoverMovieUiModel>>> = _loadMoreMovies

    private var currentGenreId: Int = 0
    private var currentPage: Int = 1
    private var isLastPage: Boolean = false

    fun loadMovies(genreId: Int) {
        currentGenreId = genreId
        currentPage = 1
        isLastPage = false

        _movies.value = Result.Loading
        viewModelScope.launch {
            _movies.value = repository.discoverMovies(genreId, currentPage)
        }
    }

    fun loadNextPage() {
        if (isLastPage) return

        currentPage++
        _loadMoreMovies.value = Result.Loading

        viewModelScope.launch {
            val result = repository.discoverMovies(currentGenreId, currentPage)
            _loadMoreMovies.value = result

            if (result is Result.Success && result.data.isEmpty()) {
                isLastPage = true
            }
        }
    }
}
