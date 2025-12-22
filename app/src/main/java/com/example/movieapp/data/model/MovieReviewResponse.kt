package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("results")
    val results: List<MovieReviewItemResponse> = emptyList()
)

data class MovieReviewItemResponse(
    @SerializedName("author")
    val author: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("created_at")
    val createdAt: String = ""
)
