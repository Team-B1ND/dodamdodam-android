package com.b1nd.dodam.data.outing.di

import com.b1nd.dodam.data.outing.PointRepository
import com.b1nd.dodam.data.outing.repository.PointRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsOutingRepository(outingRepositoryImpl: PointRepositoryImpl): PointRepository
}
