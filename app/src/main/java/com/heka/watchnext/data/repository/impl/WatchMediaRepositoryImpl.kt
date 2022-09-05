package com.heka.watchnext.data.repository.impl

import com.heka.watchnext.data.DataResult
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.model.WatchMedia
import javax.inject.Inject

class WatchMediaRepositoryImpl @Inject constructor(
    private val watchMediaRemote: WatchMediaRemoteDataSource
): WatchMediaRepository {

    override suspend fun getCinemaMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getCinemaMovies()

    override suspend fun getLatestMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getLatestMovies()

    override suspend fun getTrendingMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getTrendingMovies()

    override suspend fun getOnAirSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getOnAirSeries()

    override suspend fun getLatestSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getLatestSeries()

    override suspend fun getTrendingSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getTrendingSeries()
}

interface WatchMediaRemoteDataSource {

    suspend fun getCinemaMovies(): DataResult<List<WatchMedia>>

    suspend fun getLatestMovies(): DataResult<List<WatchMedia>>

    suspend fun getTrendingMovies(): DataResult<List<WatchMedia>>

    suspend fun getOnAirSeries(): DataResult<List<WatchMedia>>

    suspend fun getLatestSeries(): DataResult<List<WatchMedia>>

    suspend fun getTrendingSeries(): DataResult<List<WatchMedia>>

}