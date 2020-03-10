package com.example.advancelistingapp.Presenter

import android.util.Log
import com.example.advancelistingapp.Api.ApiServiceInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MoviePresenter: MovieContract.Presenter {

    private val subscription = CompositeDisposable()
    private lateinit var view: MovieContract.View
    private val apiService: ApiServiceInterface = ApiServiceInterface.create()


    override fun getAllMovie(searchKey: String) {
        val subscribe = apiService.getAllMovie(searchKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DOMAIN", it.toString())
                view.onSuccess(it)
            },{ error ->
                val msg = error.localizedMessage
                view.onError(msg)
            })
        subscription.add(subscribe)
    }

    override fun getFullMovie(id: String) {
        val subscribe = apiService.getFullMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("DOMAIN", it.toString())
                view.onFullSuccess(it)
            },{
                error ->
                val msg = error.localizedMessage
                view.onFullError(msg)
            })
        subscription.add(subscribe)
    }

    override fun subscribe() {
        TODO("Not yet implemented")
    }

    override fun unsubscribe() {
        subscription.clear()
    }

    override fun attach(view: MovieContract.View) {
        this.view = view
    }
}