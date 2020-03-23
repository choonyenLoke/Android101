package com.example.opentriva.viewmodel

import android.app.Application
import com.example.opentriva.apiservice.ApiServiceInterface
import com.example.opentriva.apiservice.RetrofitService
import com.example.opentriva.model.*
import io.reactivex.Observable

class QuestionRepository(val application: Application) {

    private val apiService: ApiServiceInterface = RetrofitService.create()

    fun getAllCategory(): Observable<Category>{
        return apiService.getAllCategory()
    }

    fun getAllCount(id: Int): Observable<Count>{
        return apiService.getAllCount(id)
    }

    fun getToken(): Observable<Token>{
        return apiService.getToken()
    }

    fun getQuestionParams(token: String, categoryId: Int?, difficulty: String?, type: String?): Observable<Result>{
        return apiService.getDefaultQuestion(token, categoryId, difficulty, type)
    }

    fun resetToken(token: String): Observable<ResetToken> {
        return apiService.reset(token)
    }

}