package com.b1nd.dodam.data.notice.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.notice.NoticeRepository
import com.b1nd.dodam.data.notice.repository.NoticeRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val noticeRepositoryModule = module {
    single<NoticeRepository> {
        NoticeRepositoryImpl(
            noticeDataSource = get(),
            dispatcher = get(named(DispatcherType.IO)),
        )
    }
}
