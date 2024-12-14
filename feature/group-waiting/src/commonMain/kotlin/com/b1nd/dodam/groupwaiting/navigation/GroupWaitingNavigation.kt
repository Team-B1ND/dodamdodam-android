package com.b1nd.dodam.groupwaiting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.groupwaiting.GroupWaitingScreen

const val GROUP_WAITING_ROUTE = "group_waiting"

fun NavController.navigateToGroupWaiting(navOptions: NavOptions? = null) =
    navigate(GROUP_WAITING_ROUTE, navOptions)

fun NavGraphBuilder.groupWaitingScreen(
    popBackStack: () -> Unit
) {
    composable(GROUP_WAITING_ROUTE) {
        GroupWaitingScreen(
            popBackStack = popBackStack
        )
    }
}