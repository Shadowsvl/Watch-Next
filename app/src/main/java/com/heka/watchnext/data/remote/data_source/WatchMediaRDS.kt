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
import javax.inject.Inject

class WatchMediaRDS @Inject constructor(
    private val tmdbApi: TmdbApi
): WatchMediaRemoteDataSource {

    override suspend fun getCinemaMovies(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getCinemaMovies().toServiceResponse(WatchMediaErrorDto::class.java)) {
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

    override suspend fun getLatestMovies(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getLatestMovies().toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getTrendingMovies().toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getOnAirSeries().toServiceResponse(WatchMediaErrorDto::class.java)) {
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

    override suspend fun getLatestSeries(): DataResult<List<WatchMedia>> {
        return withContext(Dispatchers.IO) {
            when(val response = tmdbApi.getLatestSeries().toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getTrendingSeries().toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getMovie(id).toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getTv(id).toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getSimilarMovies(id).toServiceResponse(WatchMediaErrorDto::class.java)) {
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
            when(val response = tmdbApi.getSimilarSeries(id).toServiceResponse(WatchMediaErrorDto::class.java)) {
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