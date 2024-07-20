package com.b1nd.dodam.askwakeupsong.di

import com.b1nd.dodam.askwakeupsong.AskWakeupSongViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val askWakeUpSongViewModelModule = module {
    viewModel { AskWakeupSongViewModel() }
}