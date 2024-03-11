package com.b1nd.dodam.meal

import com.b1nd.dodam.data.meal.model.Meal
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MealUiState(
    val meal: ImmutableList<Meal> = persistentListOf(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,

)
