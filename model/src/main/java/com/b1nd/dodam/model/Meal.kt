package com.b1nd.dodam.model

data class Meal(
    val date: String,
    val exists: Boolean,
    val breakfast: String?,
    val lunch: String?,
    val dinner: String?,
    val existsCalorie: Boolean,
    val breakfastCalorie: String?,
    val lunchCalorie: String?,
    val dinnerCalorie: String?,
)
