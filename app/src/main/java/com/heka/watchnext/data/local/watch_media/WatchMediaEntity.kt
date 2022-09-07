package com.heka.watchnext.data.local.watch_media

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.heka.watchnext.model.MediaType

@Entity(tableName = "watch_media")
data class WatchMediaEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Float,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "media_type") val mediaType: MediaType,
    @ColumnInfo(name = "inserted_timestamp") val insertedTimestamp: Long = System.currentTimeMillis()
)
