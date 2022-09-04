package com.heka.watchnext.model

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
)

enum class MediaType {
    Movie,
    Tv
}
