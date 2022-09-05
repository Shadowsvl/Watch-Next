package com.heka.watchnext.dependency_injection

import com.heka.watchnext.data.repository.WatchMediaRepository
import com.heka.watchnext.data.repository.impl.WatchMediaRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsWatchMediaRepository(
        watchMediaRepositoryImpl: WatchMediaRepositoryImpl
    ): WatchMediaRepository

}