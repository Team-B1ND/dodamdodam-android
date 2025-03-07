package com.b1nd.dodam.buspresetuse.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.buspresetuse.BusPresetUseScreen

const val BUS_PRESET_USE_ROUTE = "bus_preset_use"

fun NavController.navigateToBusPresetUse(navOptions: NavOptions? = null) =
    navigate(BUS_PRESET_USE_ROUTE, navOptions)

fun NavGraphBuilder.busPresetUseScreen(
    popBackStack: () -> Unit
) {
    composable(BUS_PRESET_USE_ROUTE) {
        BusPresetUseScreen(
            popBackStack = popBackStack
        )
    }
}