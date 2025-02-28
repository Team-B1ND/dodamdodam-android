package com.b1nd.dodam.club.di

import com.b1nd.dodam.club.api.ClubService
import com.b1nd.dodam.club.datasource.ClubDataSource
import org.koin.dsl.module

val clubDataSourceModule = module {
    single<ClubDataSource> {
        ClubService(get())
    }
}