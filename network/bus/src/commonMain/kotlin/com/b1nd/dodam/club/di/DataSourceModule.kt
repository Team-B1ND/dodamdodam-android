package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.api.BusService
import com.b1nd.dodam.club.datasource.BusDataSource
import org.koin.dsl.module

val busDataSourceModule = module {
    single<BusDataSource> {
        BusService(get())
    }
}
