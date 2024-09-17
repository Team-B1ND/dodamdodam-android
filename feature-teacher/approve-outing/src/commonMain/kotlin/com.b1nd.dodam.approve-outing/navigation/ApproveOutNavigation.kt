package com.b1nd.dodam.approveouting

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val APPROVE_OUTING_ROUTE = "approve_outing"

fun NavController.navigateToApproveOuting(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(APPROVE_OUTING_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.approveOutingScreen(onBackClick: () -> Unit) {
    composable(
        route = APPROVE_OUTING_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        ApproveOutScreen(
            onBackClick = onBackClick,
        )
    }
}
