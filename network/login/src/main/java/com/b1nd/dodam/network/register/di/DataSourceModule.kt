package com.b1nd.dodam.network.register.di

import com.b1nd.dodam.network.register.api.LoginService
import com.b1nd.dodam.network.register.datasource.LoginDataSource
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
    fun bindsLoginDataSource(loginService: LoginService): LoginDataSource
}
