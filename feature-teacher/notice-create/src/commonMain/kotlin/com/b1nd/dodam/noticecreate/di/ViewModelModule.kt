package com.b1nd.dodam.noticecreate.di

import com.b1nd.dodam.noticecreate.viewmodel.NoticeCreateViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noticeCreateViewModelModule = module {
    viewModel {
        NoticeCreateViewModel()
    }
}
