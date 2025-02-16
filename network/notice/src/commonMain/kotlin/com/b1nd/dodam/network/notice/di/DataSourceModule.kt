package com.b1nd.dodam.network.notice.di

import com.b1nd.dodam.network.notice.datasource.NoticeDataSource
import com.b1nd.dodam.network.notice.service.NoticeService
import org.koin.dsl.module

val noticeDatasourceModule = module {
    single<NoticeDataSource> {
        NoticeService(get())
    }
}