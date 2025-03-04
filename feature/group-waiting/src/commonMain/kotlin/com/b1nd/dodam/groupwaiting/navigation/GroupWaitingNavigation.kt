package com.b1nd.dodam.groupwaiting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.groupwaiting.GroupWaitingScreen

const val GROUP_WAITING_ROUTE = "group_waiting"

fun NavController.navigateToGroupWaiting(id: Int, name: String, navOptions: NavOptions? = null) = navigate("${GROUP_WAITING_ROUTE}/$id/$name", navOptions)

fun NavGraphBuilder.groupWaitingScreen(popBackStack: () -> Unit) {
    composable(
        route = "${GROUP_WAITING_ROUTE}/{id}/{name}",
        arguments = listOf(
            navArgument("id", { type = NavType.IntType }),
            navArgument("name", { type = NavType.StringType }),
        ),
    ) {
        GroupWaitingScreen(
            id = it.arguments?.getInt("id") ?: 0,
            name = it.arguments?.getString("name") ?: "",
            popBackStack = popBackStack,
        )
    }
}
