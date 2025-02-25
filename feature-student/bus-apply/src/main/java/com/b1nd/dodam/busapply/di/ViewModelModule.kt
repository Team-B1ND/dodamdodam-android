package com.b1nd.dodam.busapply.di

import com.b1nd.dodam.busapply.BusApplyViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val busApplyViewModelModule = module {
    viewModel {
        BusApplyViewModel()
    }
}
