package com.example.opentriva.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.opentriva.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuestionViewModel(application: Application): ViewModel() {

    private val repository = QuestionRepository()
    private val subscription = CompositeDisposable()

    private val app = application

    var tokenInit: String? = null

    var tokenNew = MutableLiveData<Token>()
    var questionResult = MutableLiveData<Result>()
    var resetToken = MutableLiveData<ResetToken>()

    var status = MutableLiveData<Boolean>()

    fun getNewToken(){
        val subscribe =  repository.getToken()
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.responseCode == 0){
                    tokenInit = it.token
                    tokenNew.value = it
                }
                else {
                    Toast.makeText(app, "Error", Toast.LENGTH_LONG).show()
                }
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getQuestionParams(token: String, categoryId: Int?, difficulty: String?, type: String?){
        val subscribe =  repository.getQuestionParams(token, categoryId, difficulty, type)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    if(it.responseCode == 0){
                        questionResult.value = it
                    }
                    else if(it.responseCode == 3)
                    {
                        getNewToken()
                        getQuestion(tokenInit.toString(), categoryId, difficulty, type)
                    }
                    else if(it.responseCode == 4){
                        getQuestionWithoutToken(categoryId, difficulty, type)
                    }
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getQuestionWithoutToken(categoryId: Int?, difficult: String?, type: String?){
        val subscribe = repository.getQuestionWithoutToken(categoryId, difficult, type)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.results.isNotEmpty()){
                    status.value = true
                    resetToken(tokenInit.toString())
                    getQuestion(tokenInit.toString(), categoryId,difficult, type)
                }
                else {
                    status.value = false
                }
            },{ error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun resetToken(token: String){
        val subscribe = repository.resetToken(token)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 //Do any logic in here
                if(it.responseCode == 0){
                    resetToken.value = it
                    //Toast.makeText(app, "Reset", Toast.LENGTH_LONG).show()
                }
                else
                {
                    resetToken(it.token)
                }
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getQuestion(token: String, categoryId: Int?, difficulty: String?, type: String?){

        val category: Int?
        val paraDifficult: String?
        val paraType: String?

        if (categoryId != 0) {
            category = categoryId
        }
        else {
            category = null
        }
        if (difficulty == "default") {
            paraDifficult = null
        }
        else {
            paraDifficult = difficulty
        }
        if (type == "default") {
            paraType = null
        }
        else {
            paraType = type
        }

        getQuestionParams(token, category, paraDifficult, paraType)

    }
}