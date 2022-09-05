package com.heka.watchnext.data.remote

import android.content.Context
import com.heka.web_helper_kt.WebClient
import com.heka.web_helper_kt.WebClientConfig
import com.heka.web_helper_kt.WebClientFactory

object BaseClientFactory : WebClientFactory {

    override fun createClient(context: Context, webClientConfig: WebClientConfig): WebClient {
        val okHttpClient = OkHttpClientHelper.createClientFromConfig(context, webClientConfig)
        return RetrofitClient(webClientConfig.baseUrl, okHttpClient)
    }

}