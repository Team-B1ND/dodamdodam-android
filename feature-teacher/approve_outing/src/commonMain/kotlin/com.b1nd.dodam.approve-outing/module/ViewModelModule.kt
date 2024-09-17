package com.b1nd.dodam.approve

import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val approveOutingViewModelModule = module {
    viewModel { ApproveOutViewModel() }
}
