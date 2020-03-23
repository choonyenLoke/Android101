package com.example.opentriva.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.opentriva.model.*

class QuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: QuestionRepository =
        QuestionRepository(application)

    val categoryList: LiveData<Category>
    val countList: LiveData<Count>
    val token: LiveData<Token>
    val questionResult: LiveData<Result>
    private val resetToken: LiveData<ResetToken>

    init {
        categoryList = repository.categoryList
        countList = repository.count
        token = repository.token
        questionResult = repository.questionResult
        resetToken = repository.reset
    }

    fun getAllCategory(){
        repository.getAllCategory()
    }

    fun getAllCount(id: Int){
        repository.getAllCount(id)
    }

    fun getToken(){
        repository.getToken()
    }

    fun getQuestionParams(token: String, categoryId: Int?, difficulty: String?, type: String?){
        repository.getQuestionParams(token, categoryId, difficulty, type)
    }

    fun resetToken(token: String){
        repository.resetToken(token)
    }
}