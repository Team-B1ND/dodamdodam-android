package com.b1nd.dodam.data.meal.model

import com.b1nd.dodam.network.meal.model.MealDetailResponse

data class MealDetail(
    val details: List<Menu>,
    val calorie: Float,
)

internal fun MealDetailResponse.toModel(): MealDetail = MealDetail(
    details = details.map { it.toModel() },
    calorie = calorie,
)