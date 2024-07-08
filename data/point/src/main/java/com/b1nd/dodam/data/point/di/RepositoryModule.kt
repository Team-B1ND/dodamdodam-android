package com.b1nd.dodam.data.point.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.point.PointRepository
import com.b1nd.dodam.data.point.repository.PointRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module

val pointRepositoryModule = module {
    single<PointRepository> {
        PointRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}