package com.b1nd.dodam.data.schedule.di

import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.data.schedule.repository.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository
}
