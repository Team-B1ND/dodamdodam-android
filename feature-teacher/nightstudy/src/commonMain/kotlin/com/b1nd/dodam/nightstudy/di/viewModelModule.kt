package com.b1nd.dodam.nightstudy.di

import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module


val nightStudyViewModelModule = module {
    viewModel { NightStudyViewModel() }
}
