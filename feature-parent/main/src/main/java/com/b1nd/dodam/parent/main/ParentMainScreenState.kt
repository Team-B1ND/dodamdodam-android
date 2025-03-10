package com.b1nd.dodam.parent.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.b1nd.dodam.all.navigation.navigateToParentAllScreen
import com.b1nd.dodam.notice.navigation.navigateToNotice
import com.b1nd.dodam.parent.main.navigation.ParentMainDestination
import com.b1nd.dodam.parnet.home.navigation.navigateToParentHome

@Composable
internal fun rememberParentMainScreenState(navController: NavHostController) = remember(
    navController,
) {
    ParentMainScreenState(navController)
}

@Stable
internal class ParentMainScreenState(
    private val navController: NavHostController,
) {
    val mainDestinations = ParentMainDestination.entries

    fun navigateToMainDestination(mainDestination: ParentMainDestination) {
        trace("Navigation: ${mainDestination.name}") {
            val mainNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (mainDestination) {
                ParentMainDestination.HOME -> navController.navigateToParentHome(mainNavOptions)
                ParentMainDestination.NOTICE -> navController.navigateToNotice(mainNavOptions)
                ParentMainDestination.ALL -> navController.navigateToParentAllScreen(mainNavOptions)
            }
        }
    }
}
