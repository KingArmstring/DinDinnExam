package com.kingarmstring.dindinnexam.network

import android.util.Log
import com.kingarmstring.dindinnexam.BuildConfig
import com.kingarmstring.dindinnexam.utils.Constants
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_END_POINT
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_TYPE_DRINK
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_TYPE_PIZZA
import com.kingarmstring.dindinnexam.utils.Constants.Companion.MENU_ITEM_TYPE_SUSHI
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkMockInterceptor(val strResponse: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val query = chain.request().url.toUri().query
        val queryType = query.split("=")[1]

        val uri = chain.request().url.toUri().toString()
        var responseString = ""

        if (uri.contains(MENU_END_POINT)) {
            when(queryType) {
                MENU_ITEM_TYPE_PIZZA -> responseString = strResponse // should hit different apis
                MENU_ITEM_TYPE_SUSHI -> responseString = strResponse // should hit different apis
                MENU_ITEM_TYPE_DRINK -> responseString = strResponse // should hit different apis
            }
        }

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
