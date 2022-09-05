package com.heka.watchnext.model

import com.heka.watchnext.BuildConfig

data class WatchMedia(
    val id: Long,
    val originalTitle: String,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Float,
    val posterPath: String,
    val backdropPath: String,
    val mediaType: MediaType
) {
    val posterUrl = BuildConfig.API_IMAGES_BASE_URL + "w342" + posterPath
    val backdropUrl = BuildConfig.API_IMAGES_BASE_URL + "w780" + backdropPath
}

enum class MediaType {
    Movie,
    Tv
}
