package com.b1nd.dodam.wakeup_song.di

import com.b1nd.dodam.wakeup_song.api.WakeupSongService
import com.b1nd.dodam.wakeup_song.datasource.WakeupSongDataSource
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
