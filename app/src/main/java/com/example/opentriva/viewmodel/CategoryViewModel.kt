package com.example.opentriva.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.opentriva.model.Category
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryViewModel(application: Application): ViewModel() {
    private val repository = QuestionRepository()
    private val subscription = CompositeDisposable()

    private val app = application

    var catList =  MutableLiveData<Category>()

    fun getAllCategory(){
        val subscribe = repository.getAllCategory()
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                catList.value = it
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }
}