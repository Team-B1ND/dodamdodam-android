package com.b1nd.dodam.data.outing.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.repository.OutingRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val outingRepositoryModule = module {
    single<OutingRepository> {
        OutingRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
