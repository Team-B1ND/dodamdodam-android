package com.b1nd.dodam.outing.di

import com.b1nd.dodam.outing.viewmodel.OutingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val outingViewModelModule = module {
    viewModel { OutingViewModel() }
}