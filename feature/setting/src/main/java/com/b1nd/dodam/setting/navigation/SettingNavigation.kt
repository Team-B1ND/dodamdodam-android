package com.b1nd.dodam.setting.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.setting.SettingScreen

const val SETTING_ROUTE = "setting"

fun NavController.navigateToSetting(navOptions: NavOptions? = null) = navigate(SETTING_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.settingScreen(
    popBackStack: () -> Unit,
    logout: () -> Unit,
) {
    composable(
        route = SETTING_ROUTE,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        SettingScreen(
            popBackStack = popBackStack,
            logout = logout,
        )
    }
}
