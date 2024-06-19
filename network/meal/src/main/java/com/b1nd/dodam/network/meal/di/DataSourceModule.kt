package com.b1nd.dodam.network.meal.di

import com.b1nd.dodam.network.meal.api.MealService
import com.b1nd.dodam.network.meal.datasource.MealDataSource
import org.koin.dsl.module

val MEAL_DATA_SOURCE_MODULE = module {
    single<MealDataSource> {
        MealService(get())
    }
}
