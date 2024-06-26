package com.b1nd.dodam.data.banner.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.banner.repository.BannerRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val bannerRepositoryModule = module {
    single<BannerRepository> {
        BannerRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
