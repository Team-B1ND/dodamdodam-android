package com.b1nd.dodam.network.nightstudy.di

import com.b1nd.dodam.network.nightstudy.api.NightStudyService
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import org.koin.dsl.module

val nightStudyDataSourceModule = module {
    single<NightStudyDataSource> {
        NightStudyService(get())
    }
}
