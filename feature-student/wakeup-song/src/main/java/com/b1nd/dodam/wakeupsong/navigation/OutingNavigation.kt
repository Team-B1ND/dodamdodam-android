package com.b1nd.dodam.wakeupsong.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.wakeupsong.WakeupSongScreen

const val WAKEUP_SONG_ROUTE = "wakeup_song"

fun NavController.navigateToWakeupSong(
    navOptions: NavOptions? = NavOptions.Builder().setLaunchSingleTop(
        true,
    ).build(),
) = navigate(WAKEUP_SONG_ROUTE, navOptions)

fun NavGraphBuilder.wakeupSongScreen(onAddWakeupSongClick: () -> Unit, popBackStack: () -> Unit) {
    composable(
        route = WAKEUP_SONG_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        WakeupSongScreen(
            onAddWakeupSongClick,
            popBackStack,
        )
    }
}
