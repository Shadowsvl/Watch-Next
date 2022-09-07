package com.heka.watchnext.dependency_injection

import com.heka.watchnext.data.local.data_source.WatchMediaLDS
import com.heka.watchnext.data.remote.data_source.WatchMediaRDS
import com.heka.watchnext.data.repository.impl.WatchMediaLocalDataSource
import com.heka.watchnext.data.repository.impl.WatchMediaRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindsWatchMediaRemoteDataSource(
        watchMediaRDS: WatchMediaRDS
    ): WatchMediaRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsWatchMediaLocalDataSource(
        watchMediaLDS: WatchMediaLDS
    ): WatchMediaLocalDataSource

}