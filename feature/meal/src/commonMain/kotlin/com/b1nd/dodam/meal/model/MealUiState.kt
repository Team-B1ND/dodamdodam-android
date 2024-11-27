package com.b1nd.dodam.meal.model

import com.b1nd.dodam.data.meal.model.Meal
import kotlinx.collections.immutable.ImmutableList

sealed interface MealUiState {
    data class Success(
        val meals: ImmutableList<Meal>,
    ) : MealUiState
    data object Error : MealUiState
    data object Loading : MealUiState
}
