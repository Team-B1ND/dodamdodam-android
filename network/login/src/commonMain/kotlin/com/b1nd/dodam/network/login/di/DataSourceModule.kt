package com.b1nd.dodam.network.login.di

import com.b1nd.dodam.network.login.api.LoginService
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import org.koin.dsl.module

val loginDataSourceModule = module {

    single<LoginDataSource> {
        LoginService(get())
    }
}
