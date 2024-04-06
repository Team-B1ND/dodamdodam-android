package com.b1nd.dodam.student.home.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.student.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = NavOptions.Builder().setLaunchSingleTop(true).build()) = navigate(HOME_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.homeScreen(
    navigateToAskOut: () -> Unit,
    navigateToMeal: () -> Unit,
    navigateToAskNightStudy: () -> Unit,
    navigateToNightStudy: () -> Unit,
    navigateToOut: () -> Unit,
    navigateToWakeupSongScreen: () -> Unit,
    navigateToAskWakeupSongScreen: () -> Unit,
) {
    composable(
        route = HOME_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        HomeScreen(
            navigateToAskOut = navigateToAskOut,
            navigateToOut = navigateToOut,
            navigateToNightStudy = navigateToNightStudy,
            navigateToMeal = navigateToMeal,
            navigateToAskNightStudy = navigateToAskNightStudy,
            navigateToWakeupSongScreen = navigateToWakeupSongScreen,
            navigateToAskWakeupSong = navigateToAskWakeupSongScreen,
        )
    }
}
