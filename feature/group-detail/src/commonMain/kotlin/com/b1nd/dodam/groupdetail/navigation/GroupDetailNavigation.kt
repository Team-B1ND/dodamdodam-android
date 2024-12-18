package com.b1nd.dodam.groupdetail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.groupdetail.GroupDetailScreen

const val GROUP_DETAIL_ROUTE = "group_detail"

fun NavController.navigateToGroupDetail(
    navOptions: NavOptions? = null
) = this.navigate(GROUP_DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.groupDetailScreen(
    popBackStack: () -> Unit,
    navigateToGroupAdd: () -> Unit,
    navigateToGroupWaiting: () -> Unit,
) {
    composable(
        route = GROUP_DETAIL_ROUTE,

        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        GroupDetailScreen(
            popBackStack = popBackStack,
            navigateToGroupAdd = navigateToGroupAdd,
            navigateToGroupWaiting = navigateToGroupWaiting
        )
    }
}