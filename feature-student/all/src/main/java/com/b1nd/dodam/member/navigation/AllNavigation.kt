package com.b1nd.dodam.member.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.member.AllScreen

const val ALL_ROUTE = "all"

fun NavController.navigateToAllScreen(navOptions: NavOptions? = null) = navigate(ALL_ROUTE, navOptions)

fun NavGraphBuilder.allScreen() {
    composable(
        route = ALL_ROUTE,
    ) {
        AllScreen()
    }
}
