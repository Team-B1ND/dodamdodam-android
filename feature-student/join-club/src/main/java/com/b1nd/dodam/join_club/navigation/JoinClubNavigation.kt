package com.b1nd.dodam.join_club.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.join_club.JoinClubScreen

const val JOIN_CLUB_ROUTE = "join_club"

fun NavController.navigateToClub(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(JOIN_CLUB_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.joinClubScreen(popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    composable(
        route = JOIN_CLUB_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        JoinClubScreen(popBackStack = popBackStack, showToast = showToast)
    }
}