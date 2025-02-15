package com.b1nd.dodam.groupdetail.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.groupdetail.GroupDetailScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val GROUP_DETAIL_ROUTE = "group_detail"

fun NavController.navigateToGroupDetail(id: Int, navOptions: NavOptions? = null) = this.navigate("${GROUP_DETAIL_ROUTE}/$id", navOptions)

fun NavGraphBuilder.groupDetailScreen(
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit,
    navigateToGroupAdd: (id: Int) -> Unit,
    navigateToGroupWaiting: (id: Int, name: String) -> Unit,
) {
    composable(
        route = "${GROUP_DETAIL_ROUTE}/{id}",
        arguments = listOf(
            navArgument("id", {
                type = NavType.IntType
            }),
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        GroupDetailScreen(
            id = it.arguments?.getInt("id") ?: 0,
            showSnackbar = showSnackbar,
            popBackStack = popBackStack,
            navigateToGroupAdd = navigateToGroupAdd,
            navigateToGroupWaiting = navigateToGroupWaiting,
        )
    }
}
