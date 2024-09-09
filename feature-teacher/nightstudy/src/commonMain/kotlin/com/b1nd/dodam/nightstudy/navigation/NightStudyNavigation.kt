package com.b1nd.dodam.nightstudy.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.nightstudy.NightStudyScreen


const val NIGHT_STUDY_ROUTE = "night"

fun NavController.navigateToNightStudy(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.nightStudyScreen() {
    composable(
        route = NIGHT_STUDY_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        NightStudyScreen()
    }
}