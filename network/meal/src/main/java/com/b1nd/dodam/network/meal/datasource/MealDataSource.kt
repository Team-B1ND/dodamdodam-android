package com.b1nd.dodam.network.meal.datasource

import com.b1nd.dodam.network.meal.model.MealResponse
import kotlinx.collections.immutable.ImmutableList

interface MealDataSource {
    suspend fun getMeal(year: Int, month: Int, day: Int): MealResponse
    suspend fun getMealOfMonth(year: Int, month: Int): ImmutableList<MealResponse>
}
