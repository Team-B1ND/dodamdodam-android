package com.b1nd.dodam.club.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.club.ClubScreen

const val CLUB_ROUTE = "club"

fun NavController.navigateToClub(navOptions: NavOptions? = null) = this.navigate(CLUB_ROUTE)

fun NavGraphBuilder.clubScreen(popBackStack: () -> Unit, navigateToApply: () -> Unit) {
    composable(
        route = CLUB_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        ClubScreen(popBackStack = popBackStack, navigateToApply = navigateToApply)
    }
}
