package com.example.movieapp.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.MovieDetailRepository
import com.example.movieapp.ui.model.MovieDetailUiModel
import com.example.movieapp.ui.model.MovieReviewUiModel
import com.example.movieapp.ui.model.MovieVideoUiModel
import com.example.registrationapp.core.ErrorMessage
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

    private val _displayedReviews = MutableLiveData<List<MovieReviewUiModel>>()
    val displayedReviews: LiveData<List<MovieReviewUiModel>> = _displayedReviews

    private var currentMovieId = 0
    private var currentReviewPage = 1
    private var isLastReviewPage = false

    private val expandedReviewKeys = mutableSetOf<String>()
    private fun reviewKey(r: MovieReviewUiModel) = "${r.author}|${r.createdAt}"

    private fun applyExpandedFlags(list: List<MovieReviewUiModel>): List<MovieReviewUiModel> {
        return list.map { it.copy(isExpanded = expandedReviewKeys.contains(reviewKey(it))) }
    }

    fun loadMovieDetail(movieId: Int) {
        currentMovieId = movieId
        _movieDetail.value = Result.Loading

        viewModelScope.launch {
            when (val result = repository.getMovieDetail(movieId)) {
                is Result.Success -> {
                    _movieDetail.value = result.data?.let { Result.Success(it) }
                        ?: Result.Error(ErrorMessage.emptyResponse)
                }
                is Result.Error -> _movieDetail.value = Result.Error(result.message)
                else -> _movieDetail.value = Result.Error(ErrorMessage.unknownError)
            }
        }
    }

    fun loadMovieVideos(movieId: Int) {
        _movieVideos.value = Result.Loading

        viewModelScope.launch {
            when (val result = repository.getMovieVideos(movieId)) {
                is Result.Success -> {
                    val data = result.data
                    _movieVideos.value =
                        if (data.isEmpty()) Result.Empty
                        else Result.Success(data)
                }
                is Result.Error -> _movieVideos.value = Result.Error(result.message)
                else -> _movieVideos.value = Result.Error(ErrorMessage.unknownError)
            }
        }
    }

    fun loadMovieReviews(movieId: Int) {
        currentMovieId = movieId
        currentReviewPage = 1
        isLastReviewPage = false

        _movieReviews.value = Result.Loading

        viewModelScope.launch {
            when (val result = repository.getMovieReviews(movieId, currentReviewPage)) {
                is Result.Success -> {
                    val data = applyExpandedFlags(result.data)
                    if (data.isEmpty()) {
                        _movieReviews.value = Result.Empty
                    } else {
                        _movieReviews.value = Result.Success(data)
                        _displayedReviews.value = data
                    }
                }
                is Result.Error -> _movieReviews.value = Result.Error(result.message)
                else -> _movieReviews.value = Result.Error(ErrorMessage.unknownError)
            }
        }
    }

    fun loadNextReviewPage() {
        if (isLastReviewPage) return

        currentReviewPage++
        _loadMoreReviews.value = Result.Loading

        viewModelScope.launch {
            when (val result =
                repository.getMovieReviews(currentMovieId, currentReviewPage)) {

                is Result.Success -> {
                    val data = applyExpandedFlags(result.data)
                    if (data.isEmpty()) {
                        isLastReviewPage = true
                        _loadMoreReviews.value = Result.Empty
                    } else {
                        val current = _displayedReviews.value.orEmpty()
                        _displayedReviews.value = current + data
                        _loadMoreReviews.value = Result.Success(data)
                    }
                }
                is Result.Error -> _loadMoreReviews.value = Result.Error(result.message)
                else -> _loadMoreReviews.value = Result.Error(ErrorMessage.unknownError)
            }
        }
    }

    fun toggleReviewExpanded(review: MovieReviewUiModel) {
        val key = reviewKey(review)
        if (expandedReviewKeys.contains(key)) {
            expandedReviewKeys.remove(key)
        } else {
            expandedReviewKeys.add(key)
        }

        _displayedReviews.value = _displayedReviews.value
            ?.map {
                if (reviewKey(it) == key)
                    it.copy(isExpanded = expandedReviewKeys.contains(key))
                else it
            }
    }
}
