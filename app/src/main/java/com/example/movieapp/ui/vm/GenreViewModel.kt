package com.example.movieapp.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.repository.GenreRepository
import com.example.movieapp.ui.model.GenreUiModel
import javax.inject.Inject
import com.example.registrationapp.core.Result
import kotlinx.coroutines.launch

class GenreViewModel @Inject constructor(
    private val repository: GenreRepository
) : ViewModel() {

    private val _genres = MutableLiveData<Result<List<GenreUiModel>>>()
    val genres: LiveData<Result<List<GenreUiModel>>> = _genres

    fun loadGenres() {
        _genres.value = Result.Loading
        viewModelScope.launch {
            _genres.value = repository.getGenreList()
        }
    }
}
