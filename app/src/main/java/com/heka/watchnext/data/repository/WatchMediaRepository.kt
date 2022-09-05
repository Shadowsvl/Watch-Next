package com.heka.watchnext.data.repository

import com.heka.watchnext.data.DataResult
import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia
import kotlinx.coroutines.flow.Flow

interface WatchMediaRepository {

    suspend fun getCinemaMovies(): DataResult<List<WatchMedia>>

    suspend fun getLatestMovies(): DataResult<List<WatchMedia>>

    suspend fun getTrendingMovies(): DataResult<List<WatchMedia>>

    suspend fun getOnAirSeries(): DataResult<List<WatchMedia>>

    suspend fun getLatestSeries(): DataResult<List<WatchMedia>>

    suspend fun getTrendingSeries(): DataResult<List<WatchMedia>>

    //fun getScrollableMedia(mediaType: MediaType): Flow<List<WatchMedia>>

    //suspend fun requestMoreMedia()

    //fun getMediaListLatest(): Flow<List<WatchMedia>>

    //fun getAllMediaList(): Flow<List<WatchMedia>>

    //suspend fun getMovie(id: Long): DataResult<WatchMedia>

    //suspend fun getSimilarMovies(id: Long): DataResult<List<WatchMedia>>

    //suspend fun getTv(id: Long): DataResult<WatchMedia>

    //suspend fun getSimilarSeries(id: Long): DataResult<List<WatchMedia>>

    //suspend fun getMedia(id: Long): WatchMedia?

    //fun isMediaInList(id: Long): Flow<Boolean>

    //suspend fun addMedia(media: WatchMedia)

    //suspend fun removeMedia(media: WatchMedia)
}