package com.b1nd.dodam.data.nightstudy.di

import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.repository.NightStudyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Singleton
    @Binds
    fun bindsNightStudyRepository(nightStudyRepositoryImpl: NightStudyRepositoryImpl): NightStudyRepository
}