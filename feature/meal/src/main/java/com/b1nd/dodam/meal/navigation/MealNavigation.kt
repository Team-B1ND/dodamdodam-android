package com.b1nd.dodam.meal.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.meal.MealScreen

const val MEAL_ROUTE = "meal"

fun NavController.navigationToMeal(navOptions: NavOptions? = null) = navigate(MEAL_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.mealScreen() {
    composable(
        route = MEAL_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        MealScreen()
    }
}
