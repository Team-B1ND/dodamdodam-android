package com.b1nd.dodam.managementnightstudy.di

import com.b1nd.dodam.managementnightstudy.viewmodel.NightStudyViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val managementNightStudyViewModelModule = module {
    viewModel { NightStudyViewModel() }
}
