package com.b1nd.dodam.point.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.point.PointScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val POINT_ROUTE = "point"

fun NavController.navigateToPoint(navOptions: NavOptions? = null) =
    this.navigate(POINT_ROUTE)

fun NavGraphBuilder.pointScreen(
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit
) {
    composable(POINT_ROUTE) {
        PointScreen(
            showSnackbar = showSnackbar,
            popBackStack = popBackStack
        )
    }
}