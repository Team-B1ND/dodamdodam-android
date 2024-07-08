package com.b1nd.dodam.register.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.register.repository.RegisterRepository
import com.b1nd.dodam.register.repositoryImpl.RegisterRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val registerRepositoryModule = module {
    single<RegisterRepository> {
        RegisterRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
