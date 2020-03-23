package com.example.opentriva.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.opentriva.apiservice.ApiServiceInterface
import com.example.opentriva.apiservice.RetrofitService
import com.example.opentriva.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuestionRepository(val application: Application) {

    val apiService: ApiServiceInterface = RetrofitService.create()
    val subscription = CompositeDisposable()

    val categoryList = MutableLiveData<Category>()
    val count = MutableLiveData<Count>()
    val token = MutableLiveData<Token>()
    val questionResult = MutableLiveData<Result>()
    val reset = MutableLiveData<ResetToken>()

    fun getAllCategory(){
        val subscribe = apiService.getAllCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                categoryList.value = it
            },{
                    error ->
                Toast.makeText(application, error.localizedMessage, Toast.LENGTH_LONG).show()
            })

        subscription.add(subscribe)
    }

    fun getAllCount(id: Int){
        val subscribe = apiService.getAllCount(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                count.value = it
            },{
                    error ->
                Toast.makeText(application, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(subscribe)
    }

    fun getToken(){
        val subscribe = apiService.getToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                token.value = it
            },{
                    error ->
                Toast.makeText(application, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(subscribe)
    }

    fun getQuestionParams(token: String, categoryId: Int?, difficulty: String?, type: String?){
        val subscribe = apiService.getDefaultQuestion(token, categoryId, difficulty, type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                questionResult.value = it
            },{
                    error ->
                Toast.makeText(application, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(subscribe)
    }

    fun resetToken(token: String){
        val subscribe = apiService.reset(token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                reset.value = it
            },{
                    error ->
                Toast.makeText(application, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(subscribe)
    }
}