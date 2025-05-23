package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.register.InfoScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(INFO_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.infoScreen(
    onNextClick: (name: String, teacherRole: String, email: String, phoneNumber: String, extensionNumber: String) -> Unit,
    onBackClick: () -> Unit,
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
) {
    composable(
        route = INFO_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        InfoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
            showSnackbar = showSnackbar,
        )
    }
}
