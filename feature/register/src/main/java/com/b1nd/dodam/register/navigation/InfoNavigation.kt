package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.register.InfoScreen

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(navOptions: NavOptions? = null) = navigate(INFO_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.infoScreen(onNextClick: (String, String, String, String, String, String) -> Unit, onBackClick: () -> Unit) {
    composable(
        route = INFO_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) + fadeIn() },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) + fadeOut() },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) + fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) + fadeOut() },
    ) {
        InfoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
        )
    }
}
