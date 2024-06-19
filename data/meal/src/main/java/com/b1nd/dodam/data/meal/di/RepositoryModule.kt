package com.b1nd.dodam.data.meal.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.meal.respository.MealRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val MEAL_REPOSITORY_MODULE = module {
    single<MealRepository> {
        MealRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}
