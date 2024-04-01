package com.b1nd.dodam.bus.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.bus.BusScreen

const val BUS_ROUTE = "bus"

fun NavController.navigateToBus(navOptions: NavOptions? = null) = navigate(BUS_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.busScreen(popBackStack: () -> Unit) {
    composable(
        route = BUS_ROUTE,
    ) {
        BusScreen(popBackStack = popBackStack)
    }
}
