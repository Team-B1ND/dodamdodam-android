package com.b1nd.dodam.groupcreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.groupcreate.GroupCreateScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val GROUP_CREATE_ROUTE = "group_create"

fun NavController.navigateToGroupCreate(navOptions: NavOptions? = null) =
    navigate(GROUP_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.groupCreateScreen(
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit
) {
    composable(GROUP_CREATE_ROUTE) {
        GroupCreateScreen(
            showSnackbar = showSnackbar,
            popBackStack = popBackStack
        )
    }
}