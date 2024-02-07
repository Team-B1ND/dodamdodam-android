package com.b1nd.dodam.network.meal.di

import com.b1nd.dodam.network.meal.api.MealService
import com.b1nd.dodam.network.meal.datasource.MealDataSource
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
    fun bindsMealDataSource(mealService: MealService): MealDataSource
}
