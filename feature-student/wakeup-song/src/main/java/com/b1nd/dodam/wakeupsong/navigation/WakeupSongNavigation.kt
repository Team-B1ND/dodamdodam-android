package com.b1nd.dodam.wakeupsong.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.wakeupsong.WakeupSongScreen

const val WAKEUP_SONG_ROUTE = "wakeup_song"

fun NavController.navigateToWakeupSong(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(WAKEUP_SONG_ROUTE, navOptions)

fun NavGraphBuilder.wakeupSongScreen(onAddWakeupSongClick: () -> Unit, popBackStack: () -> Unit, showToast: (String, String) -> Unit) {
    composable(
        route = WAKEUP_SONG_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        WakeupSongScreen(
            onAddWakeupSongClick,
            popBackStack,
            showToast = showToast,
        )
    }
}
