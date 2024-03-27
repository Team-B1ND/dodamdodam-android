package com.b1nd.dodam.member.di

import com.b1nd.dodam.member.api.MemberService
import com.b1nd.dodam.member.datasource.MemberDataSource
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
    fun bindsMealDataSource(mealService: MemberService): MemberDataSource
}
