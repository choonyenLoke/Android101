package com.example.advancelistingapp.DependencyInjection

import com.example.advancelistingapp.View.MainActivity
import dagger.Component


@Component(modules = arrayOf(MovieModule::class))
interface MovieComponent {

    fun inject (mainActivity: MainActivity)
}