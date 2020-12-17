package com.kingarmstring.dindinnexam.network

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineInterceptor: Interceptor {
    val HEADER_PRAGMA = "Pragma"
    val HEADER_CACHE_CONTROL = "Cache-Control"

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val cacheControl = CacheControl.Builder()
            .maxStale(5, TimeUnit.DAYS)
            .build()
        request = request.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .cacheControl(cacheControl)
            .build()
        return chain.proceed(request)
    }
}