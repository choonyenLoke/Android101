package com.example.advancelistingapp.Api

import com.example.advancelistingapp.Model.Movie
import com.example.advancelistingapp.Model.SearchResult
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiServiceInterface {

    @GET("/?apikey=e1e2d348&")
    fun getAllMovie(@Query("s")keyword: String) : Observable<SearchResult>

    @GET("/?apikey=e1e2d348&")
    fun getFullMovie(@Query("i")imdbID: String) : Observable<Movie>

    companion object Factory{
        private val BASE_URL = "https://www.omdbapi.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120,TimeUnit.SECONDS)
                    .build()
            ).build()

        fun create(): ApiServiceInterface{
            return retrofit.create(ApiServiceInterface::class.java)
        }
    }
}