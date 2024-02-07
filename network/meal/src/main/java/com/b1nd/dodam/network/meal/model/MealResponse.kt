package com.b1nd.dodam.network.meal.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealResponse(
    @SerialName("date") val date: String,
    @SerialName("exists") val exists: Boolean,
    @SerialName("breakfast") val breakfast: String?,
    @SerialName("lunch") val lunch: String?,
    @SerialName("dinner") val dinner: String?,
)
