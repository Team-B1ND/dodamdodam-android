package com.b1nd.dodam.student.home.di

import com.b1nd.dodam.student.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel() }
}