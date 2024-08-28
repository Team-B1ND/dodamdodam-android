package com.b1nd.dodam.login.di

import com.b1nd.dodam.login.viewmodel.LoginViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginViewModelModule = module {
    viewModel { LoginViewModel() }
}
