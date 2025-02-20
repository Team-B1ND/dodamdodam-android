package com.b1nd.dodam.parent.children_manage.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.parent.children_manage.ChildrenManageScreen

const val CHILDREN_MANAGE_ROUTE = "children_manage"

fun NavController.navigateToChildrenManageScreen(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(CHILDREN_MANAGE_ROUTE, navOptions)

fun NavGraphBuilder.childrenManageScreen(
    popBackStack: () -> Unit,
    changeBottomNavVisible: (visible: Boolean) -> Unit
) {
    composable(
        route = CHILDREN_MANAGE_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        ChildrenManageScreen(
            popBackStack = popBackStack,
            changeBottomNavVisible = changeBottomNavVisible
        )
    }
}
