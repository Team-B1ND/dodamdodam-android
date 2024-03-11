package com.b1nd.dodam.data.meal.di

import com.b1nd.dodam.data.meal.MealRepository
import com.b1nd.dodam.data.meal.respository.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository
}
