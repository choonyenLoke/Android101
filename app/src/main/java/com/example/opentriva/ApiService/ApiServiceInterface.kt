package com.example.opentriva.ApiService

import com.example.opentriva.Model.Category
import com.example.opentriva.Model.Count
import com.example.opentriva.Model.TriviaCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("/api_category.php")
    fun getAllCategory(): Observable<Category>

    @GET("/api_count.php?")
    fun getAllCount(@Query("category")id: Int): Observable<Count>
}