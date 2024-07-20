package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.BusViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val busViewModelModule = module {
    viewModel { BusViewModel() }
}