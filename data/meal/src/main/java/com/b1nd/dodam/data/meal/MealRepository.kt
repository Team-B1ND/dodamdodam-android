package com.b1nd.dodam.data.meal

import com.b1nd.dodam.common.result.Result
import com.b1nd.dodam.data.meal.model.Meal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface MealRepository {
    fun getMeal(year: Int, month: Int, day: Int): Flow<Result<Meal>>
    fun getMealOfMonth(year: Int, month: Int): Flow<Result<ImmutableList<Meal>>>
}
