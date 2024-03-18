package com.b1nd.dodam.outing.nanigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.outing.OutingScreen

const val OUTING_ROUTE = "outing"

fun NavController.navigateToOuting(navOptions: NavOptions? = null) = navigate(OUTING_ROUTE, navOptions)

fun NavGraphBuilder.outingScreen(onAddOutingClick: () -> Unit, onAddSleepOverClick: () -> Unit) {
    composable(
        route = OUTING_ROUTE,
    ) {
        OutingScreen(onAddOutingClick, onAddSleepOverClick)
    }
}
