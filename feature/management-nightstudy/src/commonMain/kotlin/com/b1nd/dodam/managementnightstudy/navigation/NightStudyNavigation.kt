package com.b1nd.dodam.managementnightstudy.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.managementnightstudy.NightStudyScreen

const val NIGHT_STUDY_ROUTE = "night"

fun NavController.navigateToManagementNightStudy(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.managementNightStudyScreen(navigateToApproveStudy: () -> Unit) {
    composable(
        route = NIGHT_STUDY_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        NightStudyScreen(
            navigateToApproveStudy = navigateToApproveStudy,
        )
    }
}
