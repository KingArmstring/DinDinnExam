package com.kingarmstring.dindinnexam.network

import android.util.Log
import com.kingarmstring.dindinnexam.BuildConfig
import com.kingarmstring.dindinnexam.utils.Constants
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class NetworkMockInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            //getting type which is pizza, sushi or drink
            val query = chain.request().url.toUri().query
            val queryType = query.split("=")[1]

            val uri = chain.request().url.toUri().toString()
            var responseString = ""

            if (uri.contains("menu")) {
                when(queryType) {
                    "pizza" -> responseString = Constants.mockPizzaResponse()
                    "sushi" -> responseString = Constants.mockSushiResponse()
                    "drinks" -> responseString = Constants.mockDrinksResponse()
                }
            }

            val response = chain.proceed(chain.request())
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
            Log.d("KingArmstring", "do we come heeeer")
            return response
        } else {
            //just to be on safe side.
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " +
                    "bound to be used only with DEBUG mode")
        }
    }
}
//.body(responseString.toByteArray().toResponseBody("application/json".toMediaTypeOrNull()))