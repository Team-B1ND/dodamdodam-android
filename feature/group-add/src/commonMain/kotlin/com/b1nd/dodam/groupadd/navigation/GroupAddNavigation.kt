package com.b1nd.dodam.groupadd.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.groupadd.GroupAddScreen

const val GROUP_ADD_ROUTE = "group_add"

fun NavController.navigateToGroupAdd(navOptions: NavOptions? = null) =
    navigate(GROUP_ADD_ROUTE, navOptions)

fun NavGraphBuilder.groupAddScreen(
    popBackStack: () -> Unit
) {
    composable(GROUP_ADD_ROUTE) {
        GroupAddScreen(
            popBackStack = popBackStack
        )
    }
}