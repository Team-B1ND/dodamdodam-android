package com.b1nd.dodam.network.meal.datasource

import com.b1nd.dodam.network.core.model.Response
import com.b1nd.dodam.network.meal.model.CalorieResponse
import com.b1nd.dodam.network.meal.model.MealResponse
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MealDataSource {
    fun getMeal(year: Int, month: Int, day: Int): MealResponse
    fun getCalorie(year: Int, month: Int, day: Int): CalorieResponse
    fun getMealOfMonth(year: Int, month: Int): ImmutableList<MealResponse>

    fun getCalorieOfMonth(year: Int, month: Int): ImmutableList<CalorieResponse>
}
