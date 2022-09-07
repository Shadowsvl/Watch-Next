package com.heka.watchnext.data.local.watch_media

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchMediaDao {

    @Query("SELECT * FROM watch_media ORDER BY inserted_timestamp DESC")
    fun flowAllMedia(): Flow<List<WatchMediaEntity>>

    @Query("SELECT * FROM watch_media ORDER BY inserted_timestamp DESC LIMIT 20")
    fun flowLatestMedia(): Flow<List<WatchMediaEntity>>

    @Query("SELECT EXISTS(SELECT * FROM watch_media WHERE id = :id)")
    fun flowCheckExistsById(id: Long): Flow<Boolean>

    @Query("SELECT * FROM watch_media WHERE id = :id")
    suspend fun getMediaById(id: Long): WatchMediaEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMedia(mediaEntity: WatchMediaEntity)

    @Query("DELETE FROM watch_media WHERE id = :id")
    suspend fun deleteMediaById(id: Long)
}