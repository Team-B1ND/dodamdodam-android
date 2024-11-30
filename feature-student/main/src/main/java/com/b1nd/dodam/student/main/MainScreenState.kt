package com.b1nd.dodam.student.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.util.trace
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.b1nd.dodam.member.navigation.navigateToAllScreen
import com.b1nd.dodam.nightstudy.navigation.navigateToNightStudy
import com.b1nd.dodam.notice.navigation.navigateToNotice
import com.b1nd.dodam.outing.nanigation.navigateToOuting
import com.b1nd.dodam.student.home.navigation.navigateToHome
import com.b1nd.dodam.student.main.navigation.MainDestination

@Composable
internal fun rememberMainScreenState(navController: NavHostController) = remember(
    navController,
) {
    MainScreenState(navController)
}

@Stable
internal class MainScreenState(
    private val navController: NavHostController,
) {
    val mainDestinations = MainDestination.entries

    fun navigateToMainDestination(mainDestination: MainDestination) {
        trace("Navigation: ${mainDestination.name}") {
            val mainNavOptions = navOptions {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (mainDestination) {
                MainDestination.HOME -> navController.navigateToHome(mainNavOptions)
                MainDestination.NOTICE -> navController.navigateToNotice(mainNavOptions)
                MainDestination.OUT -> navController.navigateToOuting(mainNavOptions)
                MainDestination.NIGHT_STUDY -> navController.navigateToNightStudy(mainNavOptions)
                MainDestination.ALL -> navController.navigateToAllScreen(mainNavOptions)
            }
        }
    }
}
