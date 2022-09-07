package com.heka.watchnext.data.remote

import com.google.gson.Gson
import com.heka.web_helper_kt.WebClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val baseUrl: String, private val okHttpClient: OkHttpClient): WebClient {

    override fun <T> getApiService(apiInterface: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(apiInterface)
    }

}