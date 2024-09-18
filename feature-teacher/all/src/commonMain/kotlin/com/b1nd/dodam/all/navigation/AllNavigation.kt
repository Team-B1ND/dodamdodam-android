package com.b1nd.dodam.all.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.all.AllScreen

const val ALL_ROUTE  = "all"

fun NavController.navigateToAll(navOptions: NavOptions? = null) =
    this.navigate(
        route = ALL_ROUTE,
        navOptions = navOptions
    )

fun NavGraphBuilder.allScreen(
    navigateToSetting: () -> Unit,
    navigateToOut: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToPoint: () -> Unit,
) {
    composable(
        route = ALL_ROUTE,
    ) {
        AllScreen(
            navigateToSetting = navigateToSetting,
            navigateToOut = navigateToOut,
            navigateToNightStudy = navigateToNightStudy,
            navigateToPoint = navigateToPoint
        )
    }
}