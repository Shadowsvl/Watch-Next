package com.heka.watchnext.dependency_injection

import android.content.Context
import androidx.room.Room
import com.heka.watchnext.data.local.AppDatabase
import com.heka.watchnext.data.local.watch_media.WatchMediaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesWatchMediaDao(db: AppDatabase): WatchMediaDao {
        return db.watchMediaDao
    }

}