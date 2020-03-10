package com.example.advancelistingapp.Presenter

import com.example.advancelistingapp.Model.Movie
import com.example.advancelistingapp.Model.SearchResult

class MovieContract {
    interface View: BaseContract.View{
        fun onSuccess(data: SearchResult)
        fun onError(msg: String?)

        fun onFullSuccess(data: Movie)
        fun onFullError(msg: String?)

    }

    interface Presenter: BaseContract.Presenter<View>{
        fun getAllMovie(searchKey: String)
        fun getFullMovie(id: String)
    }
}