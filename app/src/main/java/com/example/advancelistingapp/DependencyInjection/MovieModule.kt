package com.example.advancelistingapp.DependencyInjection

import android.app.Activity
import com.example.advancelistingapp.Presenter.MovieContract
import com.example.advancelistingapp.Presenter.MoviePresenter
import dagger.Module
import dagger.Provides


@Module
class MovieModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity{
        return activity
    }

    @Provides
    fun providePresenter(): MovieContract.Presenter{
        return MoviePresenter()
    }

}