package com.b1nd.dodam.data.meal.di

import com.b1nd.dodam.common.DispatcherType
import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.meal.respository.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.koin.core.qualifier.named
import org.koin.dsl.module

//@Module
//@InstallIn(SingletonComponent::class)
//internal interface RepositoryModule {
//
//    @Binds
//    fun bindsMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository
//}


val MEAL_REPOSITORY_MODULE = module {
    single<MealRepository> {
        MealRepositoryImpl(get(), get(named(DispatcherType.IO)))
    }
}