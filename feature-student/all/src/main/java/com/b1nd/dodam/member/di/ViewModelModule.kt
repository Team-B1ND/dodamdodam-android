package com.b1nd.dodam.member.di

import com.b1nd.dodam.member.AllViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val memberViewModelModule = module {
    viewModel { AllViewModel() }
}