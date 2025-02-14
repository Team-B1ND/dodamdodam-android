package com.b1nd.dodam.groupadd.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.groupadd.GroupAddScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val GROUP_ADD_ROUTE = "group_add"

fun NavController.navigateToGroupAdd(id: Int ,navOptions: NavOptions? = null) =
    navigate("${GROUP_ADD_ROUTE}/${id}", navOptions)

fun NavGraphBuilder.groupAddScreen(
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit
) {
    composable(
        route = "${GROUP_ADD_ROUTE}/{id}",
        arguments = listOf(
            navArgument("id", { type = NavType.IntType })
        )
    ) {
        GroupAddScreen(
            id = it.arguments?.getInt("id") ?: 0,
            showSnackbar = showSnackbar,
            popBackStack = popBackStack,
        )
    }
}