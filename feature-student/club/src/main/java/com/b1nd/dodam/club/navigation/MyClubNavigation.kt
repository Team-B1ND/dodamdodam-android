package com.b1nd.dodam.club.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.club.MyClubScreen

const val CLUB_ROUTE = "club"

fun NavController.navigateToMyClub(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(CLUB_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.myClubScreen(
    popBackStack: () -> Unit,
) {
    composable(
        route = CLUB_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        it.savedStateHandle
        MyClubScreen(
            popBackStack = popBackStack,
        )
    }
}
