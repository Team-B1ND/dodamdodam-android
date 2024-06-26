package com.b1nd.dodam.network.banner.di

import com.b1nd.dodam.network.banner.api.BannerService
import com.b1nd.dodam.network.banner.datasource.BannerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton


val bannerDataSourceModule = module {
    single<BannerDataSource> {
        BannerService(get())
    }
}