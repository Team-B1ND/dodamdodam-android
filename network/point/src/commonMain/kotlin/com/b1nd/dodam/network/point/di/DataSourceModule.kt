package com.b1nd.dodam.network.point.di

import com.b1nd.dodam.network.point.api.PointService
import com.b1nd.dodam.network.point.datasource.PointDataSource
import org.koin.dsl.module

val pointDataSourceModule = module {
    single<PointDataSource> {
        PointService(get())
    }
}
