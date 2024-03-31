package com.b1nd.dodam.wakeupsong.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.wakeupsong.WakeupSongScreen

const val WAKEUP_SONG_ROUTE = "wakeup-song"

fun NavController.navigateToWakeupSong(navOptions: NavOptions? = null) = navigate(WAKEUP_SONG_ROUTE, navOptions)

fun NavGraphBuilder.wakeupSongScreen(onAddWakeupSongClick: () -> Unit, popBackStack: () -> Unit) {
    composable(
        route = WAKEUP_SONG_ROUTE,
    ) {
        WakeupSongScreen(
            onAddWakeupSongClick,
            popBackStack,
        )
    }
}
