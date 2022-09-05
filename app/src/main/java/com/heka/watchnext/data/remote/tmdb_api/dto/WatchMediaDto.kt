package com.heka.watchnext.data.remote.tmdb_api.dto

import com.google.gson.annotations.SerializedName

data class WatchMediaDto<T> (
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_results")
    val totalResults: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("results")
    val results: List<T>?
)

data class MovieDto(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?
)

data class TvDto(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("first_air_date")
    val firstAirDate: String?,
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("original_name")
    val originalName: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val voteAverage: Float?
)