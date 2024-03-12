package com.b1nd.dodam.network.banner.di

import com.b1nd.dodam.network.banner.api.BannerService
import com.b1nd.dodam.network.banner.datasource.BannerDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    @Singleton
    fun bindsBannerDataSource(bannerService: BannerService): BannerDataSource
}
