package kr.hs.dgsw.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(navOptions: NavOptions?) = navigate(AUTH_ROUTE, navOptions)

fun NavGraphBuilder.infoScreen(onNextClick: () -> Unit, onBackClick: () -> Unit) {
    composable(route = AUTH_ROUTE) {
        infoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
        )
    }
}
