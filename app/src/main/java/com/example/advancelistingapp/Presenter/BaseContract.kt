package com.example.advancelistingapp.Presenter

class BaseContract {

    interface Presenter<in T>{
        fun subscribe()
        fun unsubscribe()
        fun attach(view: T)
    }

    interface View{

    }

}