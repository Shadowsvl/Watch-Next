package com.heka.watchnext.dependency_injection

import android.content.Context
import com.heka.watchnext.BuildConfig
import com.heka.watchnext.data.remote.BaseClientFactory
import com.heka.watchnext.data.remote.tmdb_api.TmdbApi
import com.heka.web_helper_kt.WebClientConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun providesTmdbApi(@ApplicationContext context: Context): TmdbApi {
        val webClientConfig = WebClientConfig.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .apiKey(BuildConfig.API_KEY)
            .build()
        val webClient = BaseClientFactory.createClient(context, webClientConfig)
        return webClient.getApiService(TmdbApi::class.java)
    }

}