package com.b1nd.dodam.nightstudy.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.nightstudy.NightStudyScreen

const val NIGHT_STUDY_ROUTE = "nightstudy"

fun NavController.navigateToNightStudy(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.nightStudyScreen(
    onAddClick: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
) {
    composable(
        route = NIGHT_STUDY_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        NightStudyScreen(
            onAddClick,
            showToast,
            refresh,
            dispose
        )
    }
}
