package com.b1nd.dodam.group.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.group.GroupScreen

const val GROUP_ROUTE = "group"

fun NavController.navigateToGroup(navOptions: NavOptions? = null) = navigate(GROUP_ROUTE, navOptions)

fun NavGraphBuilder.groupScreen(popBackStack: () -> Unit, isTeacher: Boolean, navigateToGroupCreate: () -> Unit, navigateToGroupDetail: (id: Int) -> Unit) {
    composable(
        route = GROUP_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        GroupScreen(
            popBackStack = popBackStack,
            isTeacher = isTeacher,
            navigateToGroupCreate = navigateToGroupCreate,
            navigateToGroupDetail = navigateToGroupDetail,
        )
    }
}
