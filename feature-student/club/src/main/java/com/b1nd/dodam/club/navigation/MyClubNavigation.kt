package com.b1nd.dodam.club.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.club.MyClubScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val MY_CLUB_ROUTE = "myClub"

fun NavController.navigateToMyClub(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(MY_CLUB_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.myClubScreen(showSnackbar: (state: SnackbarState, message: String) -> Unit, popBackStack: () -> Unit) {
    composable(
        route = MY_CLUB_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        it.savedStateHandle
        MyClubScreen(
            popBackStack = popBackStack,
            showSnackbar = showSnackbar,
        )
    }
}
