package com.heka.watchnext.data.mapper

import com.heka.watchnext.data.local.watch_media.WatchMediaEntity
import com.heka.watchnext.data.remote.tmdb_api.dto.MovieDto
import com.heka.watchnext.data.remote.tmdb_api.dto.TvDto
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia

fun WatchMediaEntity.toWatchMedia(): WatchMedia {
    return WatchMedia(
        id = id,
        originalTitle = originalTitle,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}

fun WatchMedia.toWatchMediaEntity(): WatchMediaEntity {
    return WatchMediaEntity(
        id = id,
        originalTitle = originalTitle,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        posterPath = posterPath,
        backdropPath = backdropPath,
        mediaType = mediaType
    )
}

fun MovieDto.toWatchMedia(): WatchMedia {
    return WatchMedia(
        id = id ?: 0L,
        originalTitle = originalTitle ?: "",
        title = title ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        backdropPath = backdropPath ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0f,
        mediaType = MediaType.Movie
    )
}

fun TvDto.toWatchMedia(): WatchMedia {
    return WatchMedia(
        id = id ?: 0L,
        originalTitle = originalName ?: "",
        title = name ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        backdropPath = backdropPath ?: "",
        releaseDate = firstAirDate ?: "",
        voteAverage = voteAverage ?: 0f,
        mediaType = MediaType.Tv
    )
}