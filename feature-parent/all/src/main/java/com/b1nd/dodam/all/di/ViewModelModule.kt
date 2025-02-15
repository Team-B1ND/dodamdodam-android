package com.b1nd.dodam.all.di

import com.b1nd.dodam.all.ParentAllViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val parentAllViewModelModule = module {
    viewModel { ParentAllViewModel() }
}
