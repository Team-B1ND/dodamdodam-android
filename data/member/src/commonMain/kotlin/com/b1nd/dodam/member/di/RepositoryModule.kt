package com.b1nd.dodam.member.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.member.repository.MemberRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val memberRepositoryModule = module {
    single<MemberRepository> {
        MemberRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
