package com.b1nd.dodam.register.di

import com.b1nd.dodam.register.api.RegisterService
import com.b1nd.dodam.register.datasource.RegisterDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val registerDataSourceModule = module {
    single<RegisterDataSource> {
        RegisterService(get())
    }
}