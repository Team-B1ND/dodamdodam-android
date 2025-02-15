package com.b1nd.dodam.login.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.login.LoginScreen

const val LOGIN_ROUTE = "login"

fun NavController.navigationToLogin(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(LOGIN_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.loginScreen(onBackClick: () -> Unit, navigateToMain: () -> Unit, navigateToParentMain: () -> Unit, role: String) {
    composable(
        route = LOGIN_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        LoginScreen(
            onBackClick = onBackClick,
            navigateToMain = navigateToMain,
            navigateToParentMain = navigateToParentMain,
            role = role,
        )
    }
}
