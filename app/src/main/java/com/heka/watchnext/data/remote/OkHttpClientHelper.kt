package com.heka.watchnext.data.remote

import android.content.Context
import com.heka.watchnext.BuildConfig
import com.heka.web_helper_kt.Header
import com.heka.web_helper_kt.WebClientConfig
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

object OkHttpClientHelper {

    private const val API_KEY = "api_key"

    fun createClientFromConfig(context: Context, webClientConfig: WebClientConfig): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(webClientConfig.connectTimeout.time, webClientConfig.connectTimeout.timeUnit)
            .readTimeout(webClientConfig.readTimeout.time, webClientConfig.readTimeout.timeUnit)
            .writeTimeout(webClientConfig.writeTimeout.time, webClientConfig.writeTimeout.timeUnit)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(OkHttpProfilerInterceptor())
        }

        if (webClientConfig.apiKey.isNotBlank()) {
            builder.addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalHttpUrl = originalRequest.url

                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY, webClientConfig.apiKey)
                    .build()
                val requestBuilder = originalRequest.newBuilder()
                    .url(url)

                val request = requestBuilder.build()
                return@addInterceptor chain.proceed(request)
            }
        }

        if (webClientConfig.cacheSize > 0L) {
            val cacheFile = File(context.cacheDir, "http-cache")
            val cache = Cache(cacheFile, webClientConfig.cacheSize)

            val cacheControl = CacheControl.Builder()
                .maxAge(webClientConfig.cacheMaxAge.time.toInt(), webClientConfig.cacheMaxAge.timeUnit)
                .build()

            builder.addInterceptor { chain ->
                val originalRequest = chain.request()
                val cacheHeader = originalRequest.header(Header.CACHE_CONTROL)

                val requestBuilder = originalRequest.newBuilder()

                requestBuilder.removeHeader(Header.PRAGMA)
                if (cacheHeader != Header.NO_CACHE_VALUE) {
                    requestBuilder.header(Header.CACHE_CONTROL, cacheControl.toString())
                }

                val request = requestBuilder.build()
                return@addInterceptor chain.proceed(request)
            }

            builder.addNetworkInterceptor { chain ->
                val originalRequest = chain.request()
                val originalResponse = chain.proceed(originalRequest)
                val cacheHeader = originalResponse.header(Header.CACHE_CONTROL)

                val requestBuilder = originalResponse.newBuilder()

                requestBuilder.removeHeader(Header.PRAGMA)
                if (cacheHeader == Header.NO_CACHE_VALUE) {
                    requestBuilder.header(Header.CACHE_CONTROL, Header.NO_CACHE_VALUE)
                } else {
                    requestBuilder.header(Header.CACHE_CONTROL, cacheControl.toString())
                }

                return@addNetworkInterceptor requestBuilder.build()
            }

            builder.cache(cache)
        }

        return builder.build()
    }
}