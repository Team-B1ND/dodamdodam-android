package com.b1nd.dodam.groupcreate.di

import com.b1nd.dodam.groupcreate.viewmodel.GroupCreateViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupCreateViewModelModule = module {
    viewModel {
        GroupCreateViewModel()
    }
}