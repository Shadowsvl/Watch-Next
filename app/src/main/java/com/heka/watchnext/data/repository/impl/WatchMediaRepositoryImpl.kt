package com.heka.watchnext.data.repository.impl

import com.heka.watchnext.data.DataResult
import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.successOr
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class WatchMediaRepositoryImpl @Inject constructor(
    private val watchMediaRemote: WatchMediaRemoteDataSource,
    private val watchMediaLocal: WatchMediaLocalDataSource
): WatchMediaRepository {

    private val watchMediaMutex = Mutex()

    private val indexState = MutableStateFlow(3)
    private val infiniteMediaList = mutableListOf<WatchMedia>()

    override fun getMyListLatest(): Flow<List<WatchMedia>> = watchMediaLocal.getMyListLatest()

    override suspend fun getCinemaMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getCinemaMovies()

    override suspend fun getLatestMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getLatestMovies()

    override suspend fun getTrendingMovies(): DataResult<List<WatchMedia>> = watchMediaRemote.getTrendingMovies()

    override suspend fun getOnAirSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getOnAirSeries()

    override suspend fun getLatestSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getLatestSeries()

    override suspend fun getTrendingSeries(): DataResult<List<WatchMedia>> = watchMediaRemote.getTrendingSeries()

    override fun isMediaAdded(id: Long): Flow<Boolean> = watchMediaLocal.isMediaAdded(id)

    override suspend fun addMedia(media: WatchMedia) = watchMediaLocal.addMedia(media)

    override suspend fun removeMedia(media: WatchMedia) = watchMediaLocal.removeMedia(media)

    override suspend fun getMedia(id: Long): WatchMedia? = watchMediaLocal.getMedia(id)

    override suspend fun getMovie(id: Long): DataResult<WatchMedia> = watchMediaRemote.getMovie(id)

    override suspend fun getTv(id: Long): DataResult<WatchMedia> = watchMediaRemote.getTv(id)

    override suspend fun getSimilarMovies(id: Long): DataResult<List<WatchMedia>> = watchMediaRemote.getSimilarMovies(id)

    override suspend fun getSimilarSeries(id: Long): DataResult<List<WatchMedia>> = watchMediaRemote.getSimilarSeries(id)

    override fun getInfiniteMedia(mediaType: MediaType): Flow<List<WatchMedia>> = indexState.map { index ->
        val mediaList = coroutineScope {
            val firstResult = async { getWatchMediaResult(mediaType = mediaType, page = index.minus(2)).successOr(emptyList()) }
            val secondResult = async { getWatchMediaResult(mediaType = mediaType, page = index.minus(1)).successOr(emptyList()) }
            val thirdResult = async { getWatchMediaResult(mediaType = mediaType, page = index).successOr(emptyList()) }

            firstResult.await() + secondResult.await() + thirdResult.await()
        }
        if (mediaList.isNotEmpty()) {
            watchMediaMutex.withLock {
                if (!infiniteMediaList.contains(mediaList[0])) {
                    infiniteMediaList.addAll(mediaList)
                }
            }
        }
        watchMediaMutex.withLock { infiniteMediaList.toList() }
    }

    override suspend fun requestMoreMedia() {
        indexState.update { it.plus(3) }
    }

    override suspend fun clearMedia() {
        indexState.update { 3 }
        watchMediaMutex.withLock { infiniteMediaList.clear() }
    }

    private suspend fun getWatchMediaResult(mediaType: MediaType, page: Int): DataResult<List<WatchMedia>> {
        return when(mediaType) {
            MediaType.Movie -> watchMediaRemote.getLatestMovies(page)
            MediaType.Tv -> watchMediaRemote.getLatestSeries(page)
        }
    }
}

interface WatchMediaRemoteDataSource {

    suspend fun getCinemaMovies(): DataResult<List<WatchMedia>>

    suspend fun getLatestMovies(page: Int = 1): DataResult<List<WatchMedia>>

    suspend fun getTrendingMovies(): DataResult<List<WatchMedia>>

    suspend fun getOnAirSeries(): DataResult<List<WatchMedia>>

    suspend fun getLatestSeries(page: Int = 1): DataResult<List<WatchMedia>>

    suspend fun getTrendingSeries(): DataResult<List<WatchMedia>>

    suspend fun getMovie(id: Long): DataResult<WatchMedia>

    suspend fun getTv(id: Long): DataResult<WatchMedia>

    suspend fun getSimilarMovies(id: Long): DataResult<List<WatchMedia>>

    suspend fun getSimilarSeries(id: Long): DataResult<List<WatchMedia>>

}

interface WatchMediaLocalDataSource {

    fun getMyListLatest(): Flow<List<WatchMedia>>

    fun isMediaAdded(id: Long): Flow<Boolean>

    suspend fun addMedia(media: WatchMedia)

    suspend fun removeMedia(media: WatchMedia)

    suspend fun getMedia(id: Long): WatchMedia?

}