package com.b1nd.dodam.register.di

import com.b1nd.dodam.register.repository.RegisterRepository
import com.b1nd.dodam.register.repositoryImpl.RegisterRepositoryImpl
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
    fun bindsRegisterRepository(registerRepositoryImpl: RegisterRepositoryImpl): RegisterRepository
}
