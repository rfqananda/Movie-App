package com.example.movieapp.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieRepository
import com.example.movieapp.ui.model.DiscoverMovieUiModel
import com.example.registrationapp.core.ErrorMessage
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
            when (val result = repository.discoverMovies(genreId, currentPage)) {
                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        _movies.value = Result.Empty
                        isLastPage = true
                    } else {
                        _movies.value = Result.Success(result.data)
                    }
                }
                is Result.Error -> {
                    _movies.value = Result.Error(result.message)
                }
                else -> {
                    _movies.value = Result.Error(ErrorMessage.unknownError)
                }
            }
        }
    }

    fun loadNextPage() {
        if (isLastPage) return

        currentPage++
        _loadMoreMovies.value = Result.Loading

        viewModelScope.launch {
            when (val result =
                repository.discoverMovies(currentGenreId, currentPage)) {

                is Result.Success -> {
                    if (result.data.isEmpty()) {
                        isLastPage = true
                        _loadMoreMovies.value = Result.Empty
                    } else {
                        _loadMoreMovies.value = Result.Success(result.data)
                    }
                }
                is Result.Error -> {
                    _loadMoreMovies.value = Result.Error(result.message)
                }
                else -> {
                    _loadMoreMovies.value = Result.Error(ErrorMessage.unknownError)
                }
            }
        }
    }
}
