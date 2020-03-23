package com.example.opentriva.apiservice

import com.example.opentriva.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("/api_category.php")
    fun getAllCategory(): Observable<Category>

    @GET("/api_count.php?")
    fun getAllCount(@Query("category")id: Int): Observable<Count>

    @GET("/api_token.php?command=request")
    fun getToken(): Observable<Token>

    @GET("/api.php?amount=1")
    fun getDefaultQuestion(
        @Query("token")token: String,
        @Query("category")categoryId: Int?,
        @Query("difficulty")difficult: String?,
        @Query("type")type: String?): Observable<Result>

    @GET("/api_token.php?command=reset")
    fun reset(@Query("token")token: String): Observable<ResetToken>


}