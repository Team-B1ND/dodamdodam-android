package com.b1nd.dodam.student.point.di

import com.b1nd.dodam.student.point.PointViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val pointViewModelModule = module {
    viewModel { PointViewModel() }
}