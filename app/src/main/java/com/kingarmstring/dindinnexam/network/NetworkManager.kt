package com.kingarmstring.dindinnexam.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    companion object {
        fun create() : DinDinnApi {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideClient())
                .baseUrl("https://dindinn.com/")
                .build()
                .create(DinDinnApi::class.java)
        }

        private fun provideClient() = OkHttpClient.Builder().addInterceptor(NetworkMockInterceptor()).build()

        private fun provideHttpLoggingInterceptor() : OkHttpClient {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        }
    }
}