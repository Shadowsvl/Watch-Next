package com.heka.watchnext.model

import com.heka.watchnext.BuildConfig
import com.heka.watchnext.utils.DateFormatter

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
    val scoreProgress = voteAverage / 10
    val score = if (voteAverage == 10f) "10" else String.format("%.1f", voteAverage)
    val releaseDateFormatted by lazy {
        DateFormatter.formatDatePatterns(
            date = releaseDate,
            fromPattern = "yyyy-MM-dd",
            toPattern = "dd/MM/yyyy"
        )
    }
}

enum class MediaType {
    Movie,
    Tv
}
