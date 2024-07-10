package com.b1nd.dodam.member.di

import com.b1nd.dodam.member.api.MemberService
import com.b1nd.dodam.member.datasource.MemberDataSource
import org.koin.dsl.module

val memberDataSourceModule = module {
    single<MemberDataSource> {
        MemberService(get())
    }
}
