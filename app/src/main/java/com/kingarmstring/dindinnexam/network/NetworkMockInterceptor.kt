package com.kingarmstring.dindinnexam.network

import android.util.Log
import com.kingarmstring.dindinnexam.BuildConfig
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkMockInterceptor(val strResponse: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val query = chain.request().url.toUri().query
        val queryType = query.split("=")[1]

        val uri = chain.request().url.toUri().toString()
        var responseString = ""

        if (uri.contains("menu")) {
            when(queryType) {
//                "pizza" -> responseString = FakeServer.mockPizzaResponse()
                "pizza" -> responseString = strResponse // show hit different apis
                "sushi" -> responseString = strResponse // show hit different apis
                "drinks" -> responseString = strResponse // show hit different apis
            }
        }

        //no need to use logger interceptor, we can make our log ourselves, not to mention that
        //httpLoggingInterceptor won't work unless we make http call which is not tha case here.
//        if (BuildConfig.DEBUG) {
//            Log.d("Network Mock Log", responseString)
//        }

        return chain.proceed(chain.request())
            .newBuilder()
            .code(200)
            .protocol(Protocol.HTTP_2)
            .message(responseString)
            .body(
                responseString.toByteArray()
                    .toResponseBody("application/json".toMediaTypeOrNull())
            )
            .addHeader("content-type", "application/json")
            .build()
    }
}
