package com.b1nd.dodam.point.di

import com.b1nd.dodam.point.PointViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pointViewModelModule = module {
    viewModel {
        PointViewModel()
    }
}
