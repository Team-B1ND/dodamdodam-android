package com.b1nd.dodam.wakeup_song.di

import com.b1nd.dodam.wakeup_song.WakeupSongRepository
import com.b1nd.dodam.wakeup_song.repository.WakeupSongRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsWakeupSongRepository(
        wakeupSongRepositoryImpl: WakeupSongRepositoryImpl
    ): WakeupSongRepository
}