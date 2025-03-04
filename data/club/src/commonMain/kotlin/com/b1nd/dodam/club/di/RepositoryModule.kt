package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.repository.ClubRepository
import com.b1nd.dodam.club.repositoryimpl.ClubRepositoryImpl
import com.b1nd.dodam.common.DispatcherType
import org.koin.core.qualifier.named
import org.koin.dsl.module

val clubRepositoryModule = module {
    single<ClubRepository> {
        ClubRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
