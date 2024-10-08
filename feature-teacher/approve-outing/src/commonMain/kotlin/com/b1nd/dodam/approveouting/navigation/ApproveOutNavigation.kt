package com.b1nd.dodam.approveouting

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.ui.component.SnackbarState

const val APPROVE_OUTING_ROUTE = "approve_outing"

fun NavController.navigateToApproveOuting(title: Int = 0, navOptions: NavOptions? = null) = navigate(
    route = "$APPROVE_OUTING_ROUTE/$title",
    navOptions = navOptions,
)

@ExperimentalMaterial3Api
fun NavGraphBuilder.approveOutingScreen(onBackClick: () -> Unit, showSnackbar: (state: SnackbarState, message: String) -> Unit) {
    composable(
        route = "$APPROVE_OUTING_ROUTE/{title}",
        arguments = listOf(
            navArgument("title") { type = NavType.IntType },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        ApproveOutScreen(
            onBackClick = onBackClick,
            title = it.arguments?.getInt("title") ?: 0,
            showSnackbar = showSnackbar,
        )
    }
}
