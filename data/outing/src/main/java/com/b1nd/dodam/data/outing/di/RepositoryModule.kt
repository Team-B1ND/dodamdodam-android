package com.b1nd.dodam.data.outing.di

import com.b1nd.dodam.data.outing.OutingRepository
import com.b1nd.dodam.data.outing.repository.OutingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsOutingRepository(outingRepositoryImpl: OutingRepositoryImpl): OutingRepository
}
