package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.api.BusService
import com.b1nd.dodam.bus.datasource.BusDataSource
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
    fun provideBusDataSource(busService: BusService): BusDataSource
}
