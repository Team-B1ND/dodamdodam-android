package com.b1nd.dodam.approvenightstudy

import com.b1nd.dodam.approvenightstudy.viewmodel.ApproveNightStudyViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val approveNightStudyViewModelModule = module {
    viewModel { ApproveNightStudyViewModel() }
}
