package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.register.SelectRoleScreen

const val SELECT_ROLE_ROUTE = "select_role"

fun NavController.navigateToSelectRole(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(SELECT_ROLE_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.selectRoleScreen(onBackClick: () -> Unit, navigateToChildrenManage: () -> Unit, navigateToInfo: () -> Unit) {
    composable(
        route = SELECT_ROLE_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        SelectRoleScreen(
            onBackClick = onBackClick,
            navigateToChildrenManage = navigateToChildrenManage,
            navigateToInfo = navigateToInfo,
        )
    }
}
