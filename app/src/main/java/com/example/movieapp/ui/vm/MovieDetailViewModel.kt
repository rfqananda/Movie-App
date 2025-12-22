package com.example.movieapp.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieDetailRepository
import com.example.movieapp.ui.model.MovieDetailUiModel
import com.example.movieapp.ui.model.MovieReviewUiModel
import com.example.movieapp.ui.model.MovieVideoUiModel
import javax.inject.Inject
import com.example.registrationapp.core.Result
import kotlinx.coroutines.launch

class MovieDetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository
) : ViewModel() {

    private val _movieDetail = MutableLiveData<Result<MovieDetailUiModel>>()
    val movieDetail: LiveData<Result<MovieDetailUiModel>> = _movieDetail

    private val _movieVideos = MutableLiveData<Result<List<MovieVideoUiModel>>>()
    val movieVideos: LiveData<Result<List<MovieVideoUiModel>>> = _movieVideos

    private val _movieReviews = MutableLiveData<Result<List<MovieReviewUiModel>>>()
    val movieReviews: LiveData<Result<List<MovieReviewUiModel>>> = _movieReviews

    private val _loadMoreReviews = MutableLiveData<Result<List<MovieReviewUiModel>>>()
    val loadMoreReviews: LiveData<Result<List<MovieReviewUiModel>>> = _loadMoreReviews

    private var currentMovieId: Int = 0
    private var currentReviewPage: Int = 1
    private var isLastReviewPage: Boolean = false

    fun loadMovieDetail(movieId: Int) {
        currentMovieId = movieId
        _movieDetail.value = Result.Loading
        viewModelScope.launch {
            _movieDetail.value = repository.getMovieDetail(movieId)
        }
    }

    fun loadMovieVideos(movieId: Int) {
        _movieVideos.value = Result.Loading
        viewModelScope.launch {
            _movieVideos.value = repository.getMovieVideos(movieId)
        }
    }

    fun loadMovieReviews(movieId: Int) {
        currentMovieId = movieId
        currentReviewPage = 1
        isLastReviewPage = false

        _movieReviews.value = Result.Loading
        viewModelScope.launch {
            _movieReviews.value = repository.getMovieReviews(movieId, currentReviewPage)
        }
    }

    fun loadNextReviewPage() {
        if (isLastReviewPage) return

        currentReviewPage++
        _loadMoreReviews.value = Result.Loading

        viewModelScope.launch {
            val result = repository.getMovieReviews(currentMovieId, currentReviewPage)
            _loadMoreReviews.value = result

            if (result is Result.Success && result.data.isEmpty()) {
                isLastReviewPage = true
            }
        }
    }
}
