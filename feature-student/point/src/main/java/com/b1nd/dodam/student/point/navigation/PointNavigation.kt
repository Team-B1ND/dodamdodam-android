package com.b1nd.dodam.student.point.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.student.point.PointScreen

const val POINT_ROUTE = "point"

fun NavController.navigateToPoint(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    }
) = navigate(POINT_ROUTE, navOptions)

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.pointScreen(popBackStack: () -> Unit) {
    composable(
        route = POINT_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        PointScreen(popBackStack = popBackStack)
    }
}
