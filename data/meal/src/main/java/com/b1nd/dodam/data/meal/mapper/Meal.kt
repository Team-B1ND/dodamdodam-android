package com.b1nd.dodam.data.meal.mapper

import com.b1nd.dodam.data.meal.model.Meal
import com.b1nd.dodam.model.Calorie
import com.b1nd.dodam.model.Meal
import com.b1nd.dodam.network.meal.model.CalorieResponse
import com.b1nd.dodam.network.meal.model.MealResponse

internal fun MealResponse.toModel(): Meal = Meal(
    date = date,
    exists = exists,
    breakfast = breakfast,
    lunch = lunch,
    dinner = dinner,
)

internal fun CalorieResponse.toModel(): Calorie = Calorie(
    date = date,
    exists = exists,
    breakfast = breakfast,
    lunch = lunch,
    dinner = dinner,
)