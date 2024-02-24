package com.b1nd.dodam.data.login.di

import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.data.login.repositoryimpl.LoginRepositoryImpl
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
    fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository
}
