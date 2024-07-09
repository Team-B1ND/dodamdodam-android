package com.b1nd.dodam.member.di

import com.b1nd.dodam.member.api.MemberService
import com.b1nd.dodam.member.datasource.MemberDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.dsl.module
import javax.inject.Singleton

val memberDataSourceModule = module {
    single<MemberDataSource> {
        MemberService(get())
    }
}
