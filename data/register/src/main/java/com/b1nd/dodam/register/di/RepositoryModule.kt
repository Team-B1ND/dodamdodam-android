package com.b1nd.dodam.register.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.register.repository.RegisterRepository
import com.b1nd.dodam.register.repositoryImpl.RegisterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import javax.inject.Singleton

val registerRepositoryModule = module {
    single<RegisterRepository> {
        RegisterRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}