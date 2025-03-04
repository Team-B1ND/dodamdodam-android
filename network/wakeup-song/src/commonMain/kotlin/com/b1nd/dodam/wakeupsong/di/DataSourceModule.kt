package com.b1nd.dodam.wakeupsong.di

import com.b1nd.dodam.wakeupsong.api.WakeupSongService
import com.b1nd.dodam.wakeupsong.datasource.WakeupSongDataSource
import org.koin.dsl.module

val wakeupSongDataSourceModule = module {
    single<WakeupSongDataSource> {
        WakeupSongService(get())
    }
}
