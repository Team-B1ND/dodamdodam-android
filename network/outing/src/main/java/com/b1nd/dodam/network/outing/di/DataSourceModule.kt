package com.b1nd.dodam.network.outing.di

import com.b1nd.dodam.network.outing.api.OutingService
import com.b1nd.dodam.network.outing.datasource.OutingDataSource
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
    fun bindsOutingDataSource(outingService: OutingService): OutingDataSource
}
