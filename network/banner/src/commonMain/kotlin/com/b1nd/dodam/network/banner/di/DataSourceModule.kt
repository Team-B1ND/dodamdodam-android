package com.b1nd.dodam.network.banner.di

import com.b1nd.dodam.network.banner.api.BannerService
import com.b1nd.dodam.network.banner.datasource.BannerDataSource
import org.koin.dsl.module

val bannerDataSourceModule = module {
    single<BannerDataSource> {
        BannerService(get())
    }
}
