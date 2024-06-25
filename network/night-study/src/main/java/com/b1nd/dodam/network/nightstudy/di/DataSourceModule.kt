package com.b1nd.dodam.network.nightstudy.di

import com.b1nd.dodam.network.nightstudy.api.NightStudyService
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val nightStudyDataSourceModule = module {
    single<NightStudyDataSource> {
        NightStudyService(get())
    }
}