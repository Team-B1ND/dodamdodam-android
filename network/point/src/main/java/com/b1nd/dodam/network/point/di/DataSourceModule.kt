package com.b1nd.dodam.network.point.di

import com.b1nd.dodam.network.point.api.PointService
import com.b1nd.dodam.network.point.datasource.PointDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val pointDataSourceModule = module {
    single<PointDataSource> {
        PointService(get())
    }
}