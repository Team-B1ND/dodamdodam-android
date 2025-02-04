package com.b1nd.dodam.group.di

import com.b1nd.dodam.group.viewmodel.GroupViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupViewModelModule = module {
    viewModel {
        GroupViewModel()
    }
}