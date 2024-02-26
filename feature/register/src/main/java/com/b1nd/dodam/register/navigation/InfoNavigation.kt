package com.b1nd.dodam.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.register.InfoScreen

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(navOptions: NavOptions? = null) = navigate(INFO_ROUTE, navOptions)

fun NavGraphBuilder.infoScreen(
    onNextClick: (String, String, String, String, String, String) -> Unit,
    onBackClick: () -> Unit
) {
    composable(route = INFO_ROUTE) {
        InfoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
        )
    }
}
