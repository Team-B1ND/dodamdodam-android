package com.b1nd.dodam.groupdetail.di

import com.b1nd.dodam.groupdetail.viewmodel.GroupDetailViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupDetailViewModelModule = module {
    viewModel { GroupDetailViewModel() }
}
