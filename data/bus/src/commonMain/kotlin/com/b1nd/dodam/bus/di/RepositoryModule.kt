package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.bus.repositoryimpl.BusRepositoryImpl
import com.b1nd.dodam.common.DispatcherType
import org.koin.core.qualifier.named
import org.koin.dsl.module

val busRepositoryModule = module {
    single<BusRepository> {
        BusRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
