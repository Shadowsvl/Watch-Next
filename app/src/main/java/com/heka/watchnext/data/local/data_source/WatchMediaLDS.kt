package com.heka.watchnext.data.local.data_source

import com.heka.watchnext.data.local.watch_media.WatchMediaDao
import com.heka.watchnext.data.mapper.toWatchMedia
import com.heka.watchnext.data.mapper.toWatchMediaEntity
import com.heka.watchnext.data.repository.impl.WatchMediaLocalDataSource
import com.heka.watchnext.model.WatchMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WatchMediaLDS @Inject constructor(
    private val watchMediaDao: WatchMediaDao
): WatchMediaLocalDataSource {

    override fun getMyListLatest(): Flow<List<WatchMedia>> = watchMediaDao
        .flowLatestMedia()
        .map { it.map { entity -> entity.toWatchMedia() } }
        .flowOn(Dispatchers.IO)

    override fun isMediaAdded(id: Long): Flow<Boolean> = watchMediaDao
        .flowCheckExistsById(id)
        .flowOn(Dispatchers.IO)

    override suspend fun addMedia(media: WatchMedia) {
        withContext(Dispatchers.IO) {
            watchMediaDao.insertMedia(media.toWatchMediaEntity())
        }
    }

    override suspend fun removeMedia(media: WatchMedia) {
        withContext(Dispatchers.IO) {
            watchMediaDao.deleteMediaById(media.id)
        }
    }

    override suspend fun getMedia(id: Long): WatchMedia? {
        return withContext(Dispatchers.IO) {
            watchMediaDao.getMediaById(id)?.toWatchMedia()
        }
    }

    override fun getMyList(): Flow<List<WatchMedia>> = watchMediaDao
        .flowAllMedia()
        .map { it.map { entity -> entity.toWatchMedia() } }
        .flowOn(Dispatchers.IO)

    override suspend fun searchMyList(query: String): List<WatchMedia> {
        return withContext(Dispatchers.IO) {
            watchMediaDao.searchMedia(query).map { it.toWatchMedia() }
        }
    }
}