package com.b1nd.dodam.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(navOptions: NavOptions?) = navigate(INFO_ROUTE, navOptions)

fun NavGraphBuilder.infoScreen(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    composable(route = INFO_ROUTE) {
        infoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
        )
    }
}
