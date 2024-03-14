package com.b1nd.dodam.meal.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.meal.MealScreen

const val MEAL_ROUTE = "meal"

fun NavController.navigationToMeal(navOptions: NavOptions? = null) = navigate(MEAL_ROUTE, navOptions)

fun NavGraphBuilder.mealScreen() {
    composable(
        route = MEAL_ROUTE,
    ) {
        MealScreen()
    }
}
