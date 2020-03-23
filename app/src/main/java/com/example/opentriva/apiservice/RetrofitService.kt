package com.example.opentriva.apiservice

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitService {

    private val BASE_URL = "https://opentdb.com/"

    val logging = HttpLoggingInterceptor()
        .setLevel(level = HttpLoggingInterceptor.Level.BASIC)

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120,TimeUnit.SECONDS)
                .build()
        ).build()


    fun create(): ApiServiceInterface{
        return retrofit.create(ApiServiceInterface::class.java)
    }

}