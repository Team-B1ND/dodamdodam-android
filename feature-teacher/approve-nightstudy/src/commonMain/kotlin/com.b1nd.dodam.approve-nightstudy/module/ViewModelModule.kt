package com.b1nd.dodam.approvenightstudy

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val approveNightStudyViewModelModule = module {
    viewModel { ApproveNightStudyViewModel() }
}
