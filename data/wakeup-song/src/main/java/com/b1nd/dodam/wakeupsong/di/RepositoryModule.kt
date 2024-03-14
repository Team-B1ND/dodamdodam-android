package com.b1nd.dodam.wakeupsong.di

import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import com.b1nd.dodam.wakeupsong.repository.WakeupSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsWakeupSongRepository(wakeupSongRepositoryImpl: WakeupSongRepositoryImpl): WakeupSongRepository
}
