package com.b1nd.dodam.groupwaiting.di

import com.b1nd.dodam.groupwaiting.viewmodel.GroupWaitingViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupWaitingViewModelModule = module {
    viewModel { GroupWaitingViewModel() }
}
