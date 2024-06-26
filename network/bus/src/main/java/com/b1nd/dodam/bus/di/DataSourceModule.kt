package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.api.BusService
import com.b1nd.dodam.bus.datasource.BusDataSource
import org.koin.dsl.module

val busDataSourceModule = module {
    single<BusDataSource> {
        BusService(get())
    }
}
