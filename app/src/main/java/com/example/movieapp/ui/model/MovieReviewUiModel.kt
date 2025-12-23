package com.example.movieapp.ui.model

data class MovieReviewUiModel(
    val author: String,
    val createdAt: String,
    val content: String,
    var isExpanded: Boolean = false
)
