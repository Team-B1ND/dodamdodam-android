package com.b1nd.dodam.network.outing.di

import com.b1nd.dodam.network.outing.api.OutingService
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
import org.koin.dsl.module

val outingDataSourceModule = module {
    single<OutingDataSource> {
        OutingService(get())
    }
}
