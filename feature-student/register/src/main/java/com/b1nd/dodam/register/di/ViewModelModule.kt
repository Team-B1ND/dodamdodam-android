package com.b1nd.dodam.register.di

import com.b1nd.dodam.register.viewmodel.InfoViewModel
import com.b1nd.dodam.register.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val registerViewModelModule = module {
    viewModel { RegisterViewModel() }
}
val infoViewModelModule = module {
    viewModel { InfoViewModel() }
}