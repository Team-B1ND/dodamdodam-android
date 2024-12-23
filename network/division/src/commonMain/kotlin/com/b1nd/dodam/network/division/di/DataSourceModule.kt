package com.b1nd.dodam.network.division.di

import com.b1nd.dodam.network.division.api.DivisionService
import com.b1nd.dodam.network.division.datasource.DivisionDataSource
import org.koin.dsl.module

val divisionDataSourceModule = module {
    single<DivisionDataSource> {
        DivisionService(get())
    }
}