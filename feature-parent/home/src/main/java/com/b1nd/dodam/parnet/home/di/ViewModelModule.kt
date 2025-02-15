package com.b1nd.dodam.parnet.home.di

import com.b1nd.dodam.parnet.home.ParentHomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val parentHomeViewModelModule = module {
    viewModel { ParentHomeViewModel() }
}
