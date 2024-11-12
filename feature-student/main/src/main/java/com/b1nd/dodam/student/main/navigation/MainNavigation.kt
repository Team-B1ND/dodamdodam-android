package com.b1nd.dodam.student.main.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.b1nd.dodam.student.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) = navigate(MAIN_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.mainScreen(
    navController: NavHostController,
    navigateToAskNightStudy: () -> Unit,
    navigateToAddOuting: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
    showToast: (String, String) -> Unit,
) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            navController = navController,
            navigateToAskNightStudy = navigateToAskNightStudy,
            navigateToAddOuting = navigateToAddOuting,
            navigateToSetting = navigateToSetting,
            navigateToMyPoint = navigateToMyPoint,
            navigateToAddBus = navigateToAddBus,
            navigateToWakeUpSong = navigateToWakeUpSong,
            navigateToAddWakeUpSong = navigateToAddWakeUpSong,
            showToast = showToast,
            refresh = { it.savedStateHandle["refresh"] ?: false },
            dispose = { it.savedStateHandle["refresh"] = false },
        )
    }
}
