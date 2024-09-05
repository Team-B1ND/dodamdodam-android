package com.b1nd.dodam.home.di

import com.b1nd.dodam.home.HomeViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel { HomeViewModel() }
}