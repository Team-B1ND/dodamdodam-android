package com.b1nd.dodam.all.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.all.ParentAllScreen

const val PARENT_ALL_ROUTE = "parent_all"

fun NavController.navigateToParentAllScreen(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(PARENT_ALL_ROUTE, navOptions)

fun NavGraphBuilder.parentAllScreen(navigateToSetting: () -> Unit, navigateToChildrenManage: () -> Unit, navigateToGroup: () -> Unit) {
    composable(
        route = PARENT_ALL_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        ParentAllScreen(
            navigateToSetting = navigateToSetting,
            navigateToChildrenManage = navigateToChildrenManage,
            navigateToGroup = navigateToGroup,
        )
    }
}
