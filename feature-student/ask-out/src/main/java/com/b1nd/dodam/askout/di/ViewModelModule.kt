package com.b1nd.dodam.askout.di

import com.b1nd.dodam.askout.AskOutViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val askOutViewModelModule = module {
    viewModel { AskOutViewModel() }
}
