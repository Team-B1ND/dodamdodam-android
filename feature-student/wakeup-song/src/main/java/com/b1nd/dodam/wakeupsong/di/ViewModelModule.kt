package com.b1nd.dodam.wakeupsong.di

import com.b1nd.dodam.wakeupsong.viewmodel.WakeupSongViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val wakeupSongViewModelModule = module {
    viewModel { WakeupSongViewModel() }
}