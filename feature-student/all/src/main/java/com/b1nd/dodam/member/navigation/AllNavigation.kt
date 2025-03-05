package com.b1nd.dodam.member.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.member.AllScreen

const val ALL_ROUTE = "all"

fun NavController.navigateToAllScreen(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(ALL_ROUTE, navOptions)

fun NavGraphBuilder.allScreen(
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToOuting: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
    navigateToClub: () -> Unit,
) {
    composable(
        route = ALL_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        AllScreen(
            navigateToSetting = navigateToSetting,
            navigateToMyPoint = navigateToMyPoint,
            navigateToAddBus = navigateToAddBus,
            navigateToOuting = navigateToOuting,
            navigateToWakeUpSong = navigateToWakeUpSong,
            navigateToAddWakeUpSong = navigateToAddWakeUpSong,
            navigateToClub = navigateToClub,
        )
    }
}
