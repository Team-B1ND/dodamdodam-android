package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.api.BusService
import com.b1nd.dodam.bus.datasource.BusDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val busDataSourceModule = module {
    single<BusDataSource> {
        BusService(get())
    }
}
