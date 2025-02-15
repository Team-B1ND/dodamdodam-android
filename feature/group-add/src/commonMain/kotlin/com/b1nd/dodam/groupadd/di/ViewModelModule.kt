package com.b1nd.dodam.groupadd.di

import com.b1nd.dodam.groupadd.viewmodel.GroupAddViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupAddViewModelModule = module {
    viewModel {
        GroupAddViewModel()
    }
}
