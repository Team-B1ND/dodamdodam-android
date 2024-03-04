package com.b1nd.dodam.meal.di

import com.b1nd.dodam.meal.repository.MealRepository
import com.b1nd.dodam.meal.repositoryImpl.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {
    @Binds
    @Singleton
    fun bindsMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository
}
