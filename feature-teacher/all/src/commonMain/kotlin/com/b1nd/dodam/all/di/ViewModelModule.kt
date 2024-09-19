package com.b1nd.dodam.all.di

import com.b1nd.dodam.all.AllViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val allViewModelModule = module {
    viewModel {
        AllViewModel()
    }
}