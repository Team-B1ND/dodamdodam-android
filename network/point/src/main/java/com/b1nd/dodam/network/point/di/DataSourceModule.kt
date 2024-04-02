package com.b1nd.dodam.network.point.di

import com.b1nd.dodam.network.point.api.PointService
import com.b1nd.dodam.network.point.datasource.PointDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    @Singleton
    fun bindsPointDataSource(pointService: PointService): PointDataSource
}
