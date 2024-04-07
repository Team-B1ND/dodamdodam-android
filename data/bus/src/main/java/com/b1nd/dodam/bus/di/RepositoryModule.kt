package com.b1nd.dodam.bus.di

import com.b1nd.dodam.bus.repository.BusRepository
import com.b1nd.dodam.bus.repositoryimpl.BusRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsBusRepository(busRepositoryImpl: BusRepositoryImpl): BusRepository
}
