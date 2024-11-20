package com.b1nd.dodam.data.upload.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.upload.UploadRepository
import com.b1nd.dodam.data.upload.repository.UploadRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val uploadRepositoryModule = module {
    single<UploadRepository> {
        UploadRepositoryImpl(
            network = get(),
            dispatcher = get(named(DispatcherType.IO)),
        )
    }
}
