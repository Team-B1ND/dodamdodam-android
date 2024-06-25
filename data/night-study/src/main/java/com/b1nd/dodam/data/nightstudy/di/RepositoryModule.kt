package com.b1nd.dodam.data.nightstudy.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.repository.NightStudyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import javax.inject.Singleton

val nightStudyRepositoryModule = module {
    single<NightStudyRepository> {
        NightStudyRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}