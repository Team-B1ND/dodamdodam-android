package com.b1nd.dodam.setting.di

import com.b1nd.dodam.setting.SettingViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingViewModelModule = module {
    viewModel { SettingViewModel() }
}
