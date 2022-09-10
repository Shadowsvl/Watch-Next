package com.heka.watchnext.data.remote.tmdb_api

import com.heka.watchnext.data.remote.tmdb_api.dto.MovieDto
import com.heka.watchnext.data.remote.tmdb_api.dto.TvDto
import com.heka.watchnext.data.remote.tmdb_api.dto.WatchMediaDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/now_playing?include_adult=false")
    suspend fun getCinemaMovies(
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<WatchMediaDto<MovieDto>>

    @GET("discover/movie?include_adult=false&include_video=false")
    suspend fun getLatestMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<WatchMediaDto<MovieDto>>

    @GET("trending/movie/week?include_adult=false")
    suspend fun getTrendingMovies(@Query("language") language: String): Response<WatchMediaDto<MovieDto>>

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") id: Long,
        @Query("language") language: String
    ): Response<MovieDto>

    @GET("search/movie?include_adult=false")
    suspend fun searchMovie(
        @Query("query") search: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<WatchMediaDto<MovieDto>>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getSimilarMovies(
        @Path("movie_id") id: Long,
        @Query("language") language: String
    ): Response<WatchMediaDto<MovieDto>>

    @GET("tv/on_the_air?include_adult=false")
    suspend fun getOnAirSeries(@Query("language") language: String): Response<WatchMediaDto<TvDto>>

    @GET("discover/tv?include_adult=false&include_video=false")
    suspend fun getLatestSeries(
        @Query("page") page: Int = 1,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<WatchMediaDto<TvDto>>

    @GET("trending/tv/week?include_adult=false")
    suspend fun getTrendingSeries(@Query("language") language: String): Response<WatchMediaDto<TvDto>>

    @GET("tv/{tv_id}")
    suspend fun getTv(
        @Path("tv_id") id: Long,
        @Query("language") language: String
    ): Response<TvDto>

    @GET("search/tv?include_adult=false")
    suspend fun searchTv(
        @Query("query") search: String,
        @Query("language") language: String,
        @Query("region") region: String
    ): Response<WatchMediaDto<TvDto>>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getSimilarSeries(
        @Path("tv_id") id: Long,
        @Query("language") language: String
    ): Response<WatchMediaDto<TvDto>>
}