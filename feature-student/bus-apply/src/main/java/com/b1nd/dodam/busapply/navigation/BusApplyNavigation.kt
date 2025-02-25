package com.b1nd.dodam.busapply.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.busapply.BusApplyScreen

const val BUS_APPLY_ROUTE = "busapply"

fun NavController.navigateToBusApply(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(BUS_APPLY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.busApplyScreen(popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    composable(
        route = BUS_APPLY_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        BusApplyScreen(popBackStack = popBackStack, showToast = showToast)
    }
}
