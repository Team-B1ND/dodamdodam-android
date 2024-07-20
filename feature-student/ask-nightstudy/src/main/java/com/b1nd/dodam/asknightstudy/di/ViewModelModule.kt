package com.b1nd.dodam.asknightstudy.di

import com.b1nd.dodam.asknightstudy.AskNightStudyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val askNightStudyViewModelModule = module {
    viewModel { AskNightStudyViewModel() }
}