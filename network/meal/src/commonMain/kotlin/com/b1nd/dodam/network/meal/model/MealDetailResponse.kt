package com.b1nd.dodam.network.meal.model

import kotlinx.serialization.Serializable

@Serializable
data class MealDetailResponse(
    val details: List<MenuResponse>,
    val calorie: Float,
)
