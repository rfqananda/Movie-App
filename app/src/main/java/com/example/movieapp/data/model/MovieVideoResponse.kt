package com.example.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class MovieVideoResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("results")
    val results: List<MovieVideoItemResponse> = emptyList()
)

data class MovieVideoItemResponse(
    @SerializedName("key")
    val key: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("site")
    val site: String = "",
    @SerializedName("type")
    val type: String = ""
)
