package com.b1nd.dodam.data.banner.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.banner.repository.BannerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import javax.inject.Singleton

val bannerRepositoryModule = module {
    single<BannerRepository> {
        BannerRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}