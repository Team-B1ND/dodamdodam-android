package com.b1nd.dodam.network.upload.di

import com.b1nd.dodam.network.upload.api.UploadService
import com.b1nd.dodam.network.upload.datasource.UploadDataSource
import org.koin.dsl.module

val uploadDatasourceModule = module {
    single<UploadDataSource> {
        UploadService(get())
    }
}
