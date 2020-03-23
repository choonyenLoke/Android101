package com.example.opentriva.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.opentriva.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class QuestionViewModel(application: Application): AndroidViewModel(application) {

    private val repository: QuestionRepository =
        QuestionRepository(application)
    private val subscription = CompositeDisposable()

    var catList =  MutableLiveData<Category>()
    var countList = MutableLiveData<Count>()
    var token = MutableLiveData<Token>()
    var questionResult = MutableLiveData<Result>()
    var resetToken = MutableLiveData<ResetToken>()


    fun getAllCategory(){
        val subscribe = repository.getAllCategory()
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                catList.value = it
            },{
                    error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getAllCount(id: Int){
        val subscribe = repository.getAllCount(id)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countList.value = it
            },{
                    error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getNewToken(){
        val subscribe =  repository.getToken()
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                token.value = it
            },{
                    error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getQuestionParams(token: String, categoryId: Int?, difficulty: String?, type: String?){
        val subscribe =  repository.getQuestionParams(token, categoryId, difficulty, type)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                questionResult.value = it
            },{
                    error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun resetToken(token: String){
        val subscribe = repository.resetToken(token)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                 //Do any logic in here
                resetToken.value = it
            },{
                    error ->
                Toast.makeText(getApplication(), error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }
}