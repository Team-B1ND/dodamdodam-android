package com.b1nd.dodam.data.meal.model

import com.b1nd.dodam.network.meal.model.MealResponse
import kotlinx.datetime.LocalDate

data class Meal(
    val exists: Boolean,
    val date: LocalDate,
    val breakfast: MealDetail?,
    val lunch: MealDetail?,
    val dinner: MealDetail?,
)

internal fun MealResponse.toModel(): Meal = Meal(
    exists = exists,
    date = date,
    breakfast = breakfast?.toModel(),
    lunch = lunch?.toModel(),
    dinner = dinner?.toModel(),
)
