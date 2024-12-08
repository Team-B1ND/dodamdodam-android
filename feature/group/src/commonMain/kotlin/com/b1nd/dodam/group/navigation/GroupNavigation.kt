package com.b1nd.dodam.group.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.group.GroupScreen

const val GROUP_ROUTE = "group"

fun NavController.navigateToGroup(navOptions: NavOptions? = null) =
    navigate(GROUP_ROUTE, navOptions)

fun NavGraphBuilder.groupScreen(
    popBackStack: () -> Unit
) {
    composable(GROUP_ROUTE) {
        GroupScreen(
            popBackStack = popBackStack
        )
    }
}