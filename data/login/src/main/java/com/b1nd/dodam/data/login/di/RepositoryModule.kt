package com.b1nd.dodam.data.login.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.data.login.repositoryimpl.LoginRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module
import javax.inject.Singleton

val loginRepositoryModule = module {
    single<LoginRepository> {
        LoginRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}