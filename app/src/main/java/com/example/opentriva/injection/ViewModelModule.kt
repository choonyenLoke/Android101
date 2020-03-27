package com.example.opentriva.injection

import com.example.opentriva.viewmodel.CategoryViewModel
import com.example.opentriva.viewmodel.CountViewModel
import com.example.opentriva.viewmodel.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        CategoryViewModel(get())
    }
    viewModel {
        QuestionViewModel(get())
    }
    viewModel {
        CountViewModel(get())
    }
}