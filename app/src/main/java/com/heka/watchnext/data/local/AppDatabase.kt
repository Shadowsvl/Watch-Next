package com.heka.watchnext.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.heka.watchnext.data.local.watch_media.WatchMediaDao
import com.heka.watchnext.data.local.watch_media.WatchMediaEntity

@Database(entities = [
    WatchMediaEntity::class
], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val watchMediaDao: WatchMediaDao
}