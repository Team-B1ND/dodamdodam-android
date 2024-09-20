package com.b1nd.dodam.approvenightstudy.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.approvenightstudy.ApproveNightStudyScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val APPROVE_NIGHT_STUDY_ROUTE = "approve_night_study_route"

fun NavController.navigateToApproveNightStudy(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(APPROVE_NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.approveNightStudyScreen(
    onBackClick: () -> Unit,
    showSnackbar: (snackbarState: SnackbarState, message: String) -> Unit
) {
    composable(
        route = APPROVE_NIGHT_STUDY_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        ApproveNightStudyScreen(
            onBackClick = onBackClick,
            showSnackbar = showSnackbar
        )
    }
}
