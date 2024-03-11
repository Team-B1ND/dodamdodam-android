package com.b1nd.dodam.network.schedule.di

import com.b1nd.dodam.network.schedule.api.ScheduleService
import com.b1nd.dodam.network.schedule.datasource.ScheduleDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DatasourceModule {
    @Binds
    @Singleton
    fun bindsScheduleDataSource(scheduleService: ScheduleService): ScheduleDataSource
}