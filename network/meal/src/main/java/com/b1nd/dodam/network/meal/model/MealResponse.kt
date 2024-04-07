package com.b1nd.dodam.network.meal.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    val exists: Boolean,
    val date: LocalDate,
    val breakfast: MealDetailResponse?,
    val lunch: MealDetailResponse?,
    val dinner: MealDetailResponse?,
)
