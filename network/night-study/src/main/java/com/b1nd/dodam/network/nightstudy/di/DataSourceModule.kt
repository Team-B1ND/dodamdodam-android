package com.b1nd.dodam.network.nightstudy.di

import com.b1nd.dodam.network.nightstudy.api.NightStudyService
import com.b1nd.dodam.network.nightstudy.datasource.NightStudyDataSource
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
    fun bindsNightStudyDataSource(nightStudyService: NightStudyService): NightStudyDataSource
}