package com.b1nd.dodam.busmanagement.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.busmanagement.BusManagementScreen

const val BUS_MANAGEMENT_ROUTE = "bus_management"

fun NavGraphBuilder.busManagementScreen(
    popBackStack: () -> Unit,
    navigateToBusRegister: () -> Unit,
) {
    composable(
        route = BUS_MANAGEMENT_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        BusManagementScreen(
            popBackStack = popBackStack,
            navigateToBusRegister = navigateToBusRegister,
        )
    }
}

fun NavController.navigateToBusManagement(navOptions: NavOptions? = null) =
    navigate(BUS_MANAGEMENT_ROUTE, navOptions)