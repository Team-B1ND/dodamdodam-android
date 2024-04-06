package com.b1nd.dodam.asknightstudy.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.asknightstudy.AskNightStudyScreen

const val ASK_NIGHT_STUDY_ROUTE = "ask_night_study"

fun NavController.navigateToAskNightStudy(
    navOptions: NavOptions? = NavOptions.Builder().setLaunchSingleTop(
        true,
    ).build(),
) = navigate(ASK_NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.askNightStudyScreen(popBackStack: () -> Unit) {
    composable(
        route = ASK_NIGHT_STUDY_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        AskNightStudyScreen(popBackStack = popBackStack)
    }
}
