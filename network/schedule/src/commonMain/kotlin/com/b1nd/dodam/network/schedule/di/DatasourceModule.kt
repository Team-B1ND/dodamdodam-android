package com.b1nd.dodam.network.schedule.di

import com.b1nd.dodam.network.schedule.api.ScheduleService
import com.b1nd.dodam.network.schedule.datasource.ScheduleDataSource
import org.koin.dsl.module

val scheduleDatasourceModule = module {
    single<ScheduleDataSource> {
        ScheduleService(get())
    }
}
