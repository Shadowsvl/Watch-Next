package com.heka.watchnext.data.remote.data_source

import com.heka.watchnext.data.DataResult
import com.heka.watchnext.data.Message
import com.heka.watchnext.data.mapper.toServiceResponse
import com.heka.watchnext.data.mapper.toWatchMedia
import com.heka.watchnext.data.remote.tmdb_api.TmdbApi
import com.heka.watchnext.data.remote.tmdb_api.dto.WatchMediaErrorDto
import com.heka.watchnext.data.repository.impl.WatchMediaRemoteDataSource
import com.heka.watchnext.model.WatchMedia
import com.heka.web_helper_kt.ServiceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class WatchMediaRDS @Inject constructor(
    private val tmdbApi: TmdbApi
): WatchMediaRemoteDataSource {

    private val spanishSelected = Locale.getDefault().language == "es"
    private val apiLanguage = if (spanishSelected) "es" else "en-US"
    private val apiRegion = if (spanishSelected) "MX" else "US"

    override suspend fun getCinemaMovies(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getCinemaMovies(apiLanguage, apiRegion).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        ),
                        tag = response.httpError.name
                    )
                }
            }
        }
    }

    override suspend fun getLatestMovies(page: Int): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getLatestMovies(page, apiLanguage, apiRegion).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getTrendingMovies(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getTrendingMovies(apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getOnAirSeries(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getOnAirSeries(apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getLatestSeries(page: Int): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getLatestSeries(page, apiLanguage, apiRegion).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getTrendingSeries(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getTrendingSeries(apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getMovie(id: Long): DataResult<WatchMedia> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getMovie(id, apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.toWatchMedia())
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getTv(id: Long): DataResult<WatchMedia> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getTv(id, apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.toWatchMedia())
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getSimilarMovies(id: Long): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getSimilarMovies(id, apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun getSimilarSeries(id: Long): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getSimilarSeries(id, apiLanguage).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun searchMovies(query: String): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.searchMovie(query, apiLanguage, apiRegion).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }

    override suspend fun searchSeries(query: String): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.searchTv(query, apiLanguage, apiRegion).toServiceResponse(WatchMediaErrorDto::class.java)) {
                is ServiceResponse.Ok -> DataResult.Success(response.data?.results?.map { it.toWatchMedia() })
                is ServiceResponse.Error -> {
                    DataResult.Error(
                        Message.Text(
                            response.errorDto?.statusMessage ?: response.errorMessage
                        )
                    )
                }
            }
        }
    }
}