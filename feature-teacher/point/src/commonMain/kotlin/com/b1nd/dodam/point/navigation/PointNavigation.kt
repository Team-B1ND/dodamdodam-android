package com.b1nd.dodam.point.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.point.PointScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val POINT_ROUTE = "point"

fun NavController.navigateToPoint(navOptions: NavOptions? = null) = this.navigate(POINT_ROUTE)

fun NavGraphBuilder.pointScreen(showSnackbar: (state: SnackbarState, message: String) -> Unit, popBackStack: () -> Unit) {
    composable(
        route = POINT_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        PointScreen(
            showSnackbar = showSnackbar,
            popBackStack = popBackStack,
        )
    }
}
