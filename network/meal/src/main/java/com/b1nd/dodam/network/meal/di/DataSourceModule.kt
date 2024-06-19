package com.b1nd.dodam.network.meal.di

import com.b1nd.dodam.network.meal.api.MealService
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import org.koin.dsl.module

val mealDataSourceModule = module {
    single<MealDataSource> {
        MealService(get())
    }
}
