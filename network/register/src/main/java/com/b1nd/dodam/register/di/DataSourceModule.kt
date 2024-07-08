package com.b1nd.dodam.register.di

import com.b1nd.dodam.register.api.RegisterService
import com.b1nd.dodam.register.datasource.RegisterDataSource
import org.koin.dsl.module

val registerDataSourceModule = module {
    single<RegisterDataSource> {
        RegisterService(get())
    }
}
