package com.b1nd.dodam.notice.di

import com.b1nd.dodam.notice.viewmodel.NoticeViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val noticeViewModelModule = module {
    viewModel {
        NoticeViewModel()
    }
}
