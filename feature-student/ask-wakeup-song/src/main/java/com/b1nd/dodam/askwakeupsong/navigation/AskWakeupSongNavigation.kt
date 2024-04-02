package com.b1nd.dodam.askwakeupsong.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.askwakeupsong.AskWakeupSongScreen

const val ASK_WAKEUP_SONG_ROUTE = "ask_wakeup_song"

fun NavController.navigateToAskWakeupSong(navOptions: NavOptions? = null) = navigate(
    ASK_WAKEUP_SONG_ROUTE,
    navOptions,
)

fun NavGraphBuilder.askWakeupSongScreen(popBackStack: () -> Unit) {
    composable(
        route = ASK_WAKEUP_SONG_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        AskWakeupSongScreen(popBackStack = popBackStack)
    }
}
