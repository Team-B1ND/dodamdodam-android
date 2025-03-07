package com.b1nd.dodam.buspreset.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.buspreset.BusPresetScreen

const val BUS_PRESET_ROUTE = "bus_preset"

fun NavController.navigateToBusPreset(navOptions: NavOptions? = null) =
    navigate(BUS_PRESET_ROUTE, navOptions)

fun NavGraphBuilder.busPresetScreen(
    popBackStack: () -> Unit,
    navigateToBusPresetCreate: () -> Unit,
    navigateToBusPresetUse: () -> Unit,
) {
    composable(BUS_PRESET_ROUTE) {
        BusPresetScreen(
            popBackStack = popBackStack,
            navigateToBusPresetCreate = navigateToBusPresetCreate,
            navigateToBusPresetUse = navigateToBusPresetUse,
        )
    }
}