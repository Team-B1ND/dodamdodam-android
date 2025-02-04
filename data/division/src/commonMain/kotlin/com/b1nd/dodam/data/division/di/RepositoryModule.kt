package com.b1nd.dodam.data.division.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.division.DivisionRepository
import com.b1nd.dodam.data.division.repository.DivisionRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val divisionRepositoryModule = module {
    single<DivisionRepository> {
        DivisionRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}