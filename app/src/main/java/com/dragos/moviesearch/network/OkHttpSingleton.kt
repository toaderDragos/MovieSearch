package com.dragos.moviesearch.network

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
// Standard connection to the API
object OkHttpSingleton {
    val instance: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(ApiConstant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(ApiConstant.OKHTTP_TIMEOUT, TimeUnit.SECONDS)
        .build()
}
