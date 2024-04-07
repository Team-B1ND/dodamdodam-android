package com.b1nd.dodam.data.banner.di

import com.b1nd.dodam.data.banner.BannerRepository
import com.b1nd.dodam.data.banner.repository.BannerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsBannerRepository(bannerRepositoryImpl: BannerRepositoryImpl): BannerRepository
}
