package com.b1nd.dodam.outing.nanigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.outing.OutingScreen

const val OUTING_ROUTE = "outing"

fun NavController.navigateToOuting(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(OUTING_ROUTE, navOptions)

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.outingScreen(onAddOutingClick: () -> Unit, showToast: (String, String) -> Unit, refresh: () -> Boolean, dispose: () -> Unit) {
    composable(
        route = OUTING_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        OutingScreen(onAddOutingClick, showToast, refresh, dispose)
    }
}
