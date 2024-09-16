package com.b1nd.dodam.outing.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.outing.OutingScreen

const val OUTING_ROUTE = "outing"

fun NavController.navigateToOuting(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(OUTING_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.outingScreen() {
    composable(
        route = OUTING_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        OutingScreen()
    }
}
