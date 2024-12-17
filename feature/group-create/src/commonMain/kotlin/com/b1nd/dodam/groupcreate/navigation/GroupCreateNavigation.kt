package com.b1nd.dodam.groupcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.groupcreate.GroupCreateScreen

const val GROUP_CREATE_ROUTE = "group_create"

fun NavController.navigateToGroupCreate(navOptions: NavOptions? = null) =
    navigate(GROUP_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.groupCreateScreen(
    popBackStack: () -> Unit
) {
    composable(GROUP_CREATE_ROUTE) {
        GroupCreateScreen(
            popBackStack = popBackStack
        )
    }
}