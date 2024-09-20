package com.b1nd.dodam.data.schedule.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.schedule.ScheduleRepository
import com.b1nd.dodam.data.schedule.repository.ScheduleRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val scheduleRepositoryModule = module {
    single<ScheduleRepository> {
        ScheduleRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
