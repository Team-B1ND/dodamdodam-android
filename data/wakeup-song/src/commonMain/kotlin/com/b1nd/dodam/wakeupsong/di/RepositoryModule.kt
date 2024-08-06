package com.b1nd.dodam.wakeupsong.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.wakeupsong.WakeupSongRepository
import com.b1nd.dodam.wakeupsong.repository.WakeupSongRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val wakeupSongRepositoryModule = module {
    single<WakeupSongRepository> {
        WakeupSongRepositoryImpl(
            network = get(),
            dispatcher = get(named(DispatcherType.IO)),
        )
    }
}
