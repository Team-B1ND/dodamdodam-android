package com.b1nd.dodam.editmemberinfo.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.editmemberinfo.EditMemberInfoScreen

const val EDIT_MEMBER_INFO_ROUTE = "login"

fun NavController.navigationToEditMemberInfo(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(EDIT_MEMBER_INFO_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.editMemberInfoScreen(popBackStack: () -> Unit) {
    composable(
        route = EDIT_MEMBER_INFO_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        EditMemberInfoScreen(
            popBackStack = popBackStack
        )
    }
}
