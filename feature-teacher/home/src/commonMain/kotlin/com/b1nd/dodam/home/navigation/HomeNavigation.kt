package com.b1nd.dodam.home.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = this.navigate(HOME_ROUTE)

fun NavGraphBuilder.homeScreen(navigateToMeal: () -> Unit, navigateToOut: () -> Unit, navigateToSleep: () -> Unit, navigateToNightStudy: () -> Unit) {
    composable(
        route = HOME_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        HomeScreen(
            navigateToMeal = navigateToMeal,
            navigateToOuting = navigateToOut,
            navigateToNightStudy = navigateToNightStudy,
            navigateToSleep = navigateToSleep,
        )
    }
}
