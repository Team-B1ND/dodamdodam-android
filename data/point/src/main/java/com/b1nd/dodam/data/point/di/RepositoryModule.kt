package com.b1nd.dodam.data.point.di

import com.b1nd.dodam.data.point.PointRepository
import com.b1nd.dodam.data.point.repository.PointRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsPointsRepository(pointRepositoryImpl: PointRepositoryImpl): PointRepository
}
