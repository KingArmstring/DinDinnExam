package com.kingarmstring.dindinnexam.network

import android.app.Application
import android.content.Context
import com.kingarmstring.dindinnexam.BuildConfig
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager(val strResponse: String) {

    fun create() : DinDinnApi {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideHttpLoggingInterceptor())
            .client(provideMockClient())
            .baseUrl("https://dindinn.com/")
            .build()
            .create(DinDinnApi::class.java)
    }

    private fun provideMockClient() = OkHttpClient.Builder().addInterceptor(NetworkMockInterceptor(strResponse)).build()

    /**
     * We can use offline interceptor if we want very quick simple data caching but in real life
     * work I would not count on it, I prefer to make the caching using SQLite, Room, or Realm
     * (prefer Room of course)
     */
    private fun provideOfflineClient() = OkHttpClient.Builder().addInterceptor(OfflineInterceptor()).build()

    /**
     * Used it in debugging and debugging only, we can achive that using if BuildConfig.BEBUG
     * like shown below, but it won't be useful if data is not gotten via http, but since we
     * are using my mocking interceptor I can log data myself instead there in the
     * NetworkMockInterceptor
     */
    private fun provideHttpLoggingInterceptor() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
    }

//    companion object {
//        fun create() : DinDinnApi {
//            return Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(provideHttpLoggingInterceptor())
//                .client(provideMockClient())
//                .baseUrl("https://dindinn.com/")
//                .build()
//                .create(DinDinnApi::class.java)
//        }
//
//        private fun provideMockClient() = OkHttpClient.Builder().addInterceptor(NetworkMockInterceptor()).build()
//
//        /**
//         * We can use offline interceptor if we want very quick simple data caching but in real life
//         * work I would not count on it, I prefer to make the caching using SQLite, Room, or Realm
//         * (prefer Room of course)
//         */
//        private fun provideOfflineClient() = OkHttpClient.Builder().addInterceptor(OfflineInterceptor()).build()
//
//        /**
//         * Used it in debugging and debugging only, we can achive that using if BuildConfig.BEBUG
//         * like shown below, but it won't be useful if data is not gotten via http, but since we
//         * are using my mocking interceptor I can log data myself instead there in the
//         * NetworkMockInterceptor
//         */
//        private fun provideHttpLoggingInterceptor() : OkHttpClient {
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            if (BuildConfig.DEBUG) {
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//            }else {
//                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
//            }
//            return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
//        }
//
//    }
}