package com.b1nd.dodam.member.di

import com.b1nd.dodam.member.MemberRepository
import com.b1nd.dodam.member.repository.MemberRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsMemberRepository(memberRepositoryImpl: MemberRepositoryImpl): MemberRepository
}
