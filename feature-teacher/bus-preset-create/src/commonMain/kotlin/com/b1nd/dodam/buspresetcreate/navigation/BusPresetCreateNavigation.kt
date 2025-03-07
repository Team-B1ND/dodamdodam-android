package com.b1nd.dodam.buspresetcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.buspresetcreate.BusPresetCreateScreen

const val BUS_PRESET_CREATE_ROUTE = "bus_preset_create"

fun NavController.navigateToBusPresetCreate(navOptions: NavOptions? = null) =
    navigate(BUS_PRESET_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.busPresetCreateScreen(
    popBackStack: () -> Unit
) {
    composable(BUS_PRESET_CREATE_ROUTE) {
        BusPresetCreateScreen(
            popBackStack = popBackStack
        )
    }
}