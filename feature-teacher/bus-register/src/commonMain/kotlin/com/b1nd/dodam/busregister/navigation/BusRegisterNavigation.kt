package com.b1nd.dodam.busregister.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.busregister.BusRegisterScreen

const val BUS_REGISTER_ROUTE = "bus_register"

fun NavController.navigateToBusRegister(navOptions: NavOptions? = null) =
    navigate(BUS_REGISTER_ROUTE, navOptions)

fun NavGraphBuilder.busRegisterScreen(
    popBackStack: () -> Unit,
    navigateToBusPreset: () -> Unit,
) {
    composable(BUS_REGISTER_ROUTE) {
        BusRegisterScreen(
            popBackStack = popBackStack,
            navigateToBusPreset = navigateToBusPreset,
        )
    }
}