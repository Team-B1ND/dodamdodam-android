package com.b1nd.dodam.busmanagement.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.busmanagement.BusManagementScreen

const val BUS_MANAGEMENT_ROUTE = "bus_management"

fun NavGraphBuilder.busManagementScreen(
    popBackStack: () -> Unit,
) {
    composable(
        route = BUS_MANAGEMENT_ROUTE,
    ) {
        BusManagementScreen(
            popBackStack = popBackStack
        )
    }
}

fun NavController.navigateToBusManagement(navOptions: NavOptions? = null) =
    navigate(BUS_MANAGEMENT_ROUTE, navOptions)