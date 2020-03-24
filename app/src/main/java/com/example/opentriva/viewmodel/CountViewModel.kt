package com.example.opentriva.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.opentriva.model.Category
import com.example.opentriva.model.Count
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountViewModel (application: Application): AndroidViewModel(application){

    private val repository = QuestionRepository()
    private val subscription = CompositeDisposable()
    private val app = application

    var catList =  MutableLiveData<Category>()
    var countList = MutableLiveData<Count>()

    fun getCategory(){
        val dispose = repository.getAllCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                catList.value = it
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

    fun getCount(id: Int){
        val subscribe = repository.getAllCount(id)
        val dispose = subscribe.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                countList.value = it
            },{
                    error ->
                Toast.makeText(app, error.localizedMessage, Toast.LENGTH_LONG).show()
            })
        subscription.add(dispose)
    }

}