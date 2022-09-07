package com.heka.watchnext.data.remote.tmdb_api

import com.heka.watchnext.data.remote.tmdb_api.dto.MovieDto
import com.heka.watchnext.data.remote.tmdb_api.dto.TvDto
import com.heka.watchnext.data.remote.tmdb_api.dto.WatchMediaDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/now_playing?language=es&region=MX")
    suspend fun getCinemaMovies(): Response<WatchMediaDto<MovieDto>>

    @GET("discover/movie?language=es&include_adult=false")
    suspend fun getLatestMovies(@Query("page") page: Int = 1): Response<WatchMediaDto<MovieDto>>

    @GET("trending/movie/week?language=es")
    suspend fun getTrendingMovies(): Response<WatchMediaDto<MovieDto>>

    @GET("movie/{movie_id}?language=es")
    suspend fun getMovie(@Path("movie_id") id: Long): Response<MovieDto>

    @GET("search/movie?language=es&include_adult=false")
    suspend fun searchMovie(@Query("query") search: String): Response<WatchMediaDto<MovieDto>>

    @GET("movie/{movie_id}/similar?language=es")
    suspend fun getSimilarMovies(@Path("movie_id") id: Long): Response<WatchMediaDto<MovieDto>>

    @GET("tv/on_the_air?language=es")
    suspend fun getOnAirSeries(): Response<WatchMediaDto<TvDto>>

    @GET("discover/tv?language=es&include_adult=false")
    suspend fun getLatestSeries(@Query("page") page: Int = 1): Response<WatchMediaDto<TvDto>>

    @GET("trending/tv/week?language=es")
    suspend fun getTrendingSeries(): Response<WatchMediaDto<TvDto>>

    @GET("tv/{tv_id}?language=es")
    suspend fun getTv(@Path("tv_id") id: Long): Response<TvDto>

    @GET("search/tv?language=es&include_adult=false")
    suspend fun searchTv(@Query("query") search: String): Response<WatchMediaDto<TvDto>>

    @GET("tv/{tv_id}/similar?language=es")
    suspend fun getSimilarSeries(@Path("tv_id") id: Long): Response<WatchMediaDto<TvDto>>
}