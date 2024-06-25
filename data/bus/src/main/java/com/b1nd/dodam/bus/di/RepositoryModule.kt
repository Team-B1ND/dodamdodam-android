package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.bus.repositoryimpl.BusRepositoryImpl
import com.b1nd.dodam.common.DispatcherType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import javax.inject.Singleton

val busRepositoryModule = module {
    single<BusRepository> {
        BusRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}