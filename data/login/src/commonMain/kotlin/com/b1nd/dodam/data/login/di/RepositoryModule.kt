package com.b1nd.dodam.data.login.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.login.repository.LoginRepository
import com.b1nd.dodam.data.login.repositoryimpl.LoginRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val loginRepositoryModule = module {
    single<LoginRepository> {
        LoginRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
