package com.b1nd.dodam.wakeupsong.di

import com.b1nd.dodam.wakeupsong.api.WakeupSongService
import com.b1nd.dodam.wakeupsong.datasource.WakeupSongDataSource
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
    fun bindsWakeupSongDataSource(wakeupSongService: WakeupSongService): WakeupSongDataSource
}
