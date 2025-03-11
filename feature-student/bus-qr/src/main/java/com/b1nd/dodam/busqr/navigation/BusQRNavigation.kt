package com.b1nd.dodam.busqr.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.busqr.BusQRScreen

const val BUS_QR_ROUTE = "bus_qr/{id}"

fun NavController.navigateToBusQR(navOptions: NavOptions? = null) =
    navigate(BUS_QR_ROUTE, navOptions)

fun NavGraphBuilder.busQRScreen(
    popBackStack: () -> Unit,
) {
    composable(
        route = BUS_QR_ROUTE,
        arguments = listOf(
            navArgument("id", {
                type = NavType.IntType
            })
        )
    ) { navBackStackEntry ->
        BusQRScreen(
            popBackStack = popBackStack,
            id = navBackStackEntry.arguments?.getInt("id") ?: 0
        )
    }
}