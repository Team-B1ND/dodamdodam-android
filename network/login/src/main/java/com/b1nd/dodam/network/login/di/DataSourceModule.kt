package com.b1nd.dodam.network.login.di

import com.b1nd.dodam.network.login.api.LoginService
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.koin.dsl.module

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    @Singleton
    fun bindsLoginDataSource(loginService: LoginService): LoginDataSource
}

val loginDataSourceModule = module {

    single<LoginDataSource> {
        LoginService(get())
    }
}
