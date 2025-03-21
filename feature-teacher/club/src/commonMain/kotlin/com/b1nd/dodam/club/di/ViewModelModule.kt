package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.ClubViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val clubViewModelModule = module {
    viewModel {
        ClubViewModel()
    }
}
