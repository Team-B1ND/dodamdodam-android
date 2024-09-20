package com.b1nd.dodam.data.nightstudy.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.nightstudy.NightStudyRepository
import com.b1nd.dodam.data.nightstudy.repository.NightStudyRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val nightStudyRepositoryModule = module {
    single<NightStudyRepository> {
        NightStudyRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
