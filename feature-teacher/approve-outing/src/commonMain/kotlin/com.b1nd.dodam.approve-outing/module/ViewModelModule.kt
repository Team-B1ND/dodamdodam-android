package com.b1nd.dodam.approveouting

import com.b1nd.dodam.approveouting.viewmodel.ApproveOutViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val approveOutingViewModelModule = module {
    viewModel { ApproveOutViewModel() }
}
