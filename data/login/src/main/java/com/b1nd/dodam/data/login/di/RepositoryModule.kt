package com.b1nd.dodam.data.login.di

import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.data.login.repository_impl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Provides
    fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository = loginRepositoryImpl
}
