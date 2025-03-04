package com.b1nd.dodam.outing.di

import com.b1nd.dodam.outing.viewmodel.OutViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val outingViewModelModule = module {
    viewModel { OutViewModel() }
}
