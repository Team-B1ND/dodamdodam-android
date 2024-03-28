package com.b1nd.dodam.student.main.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.student.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(navOptions: NavOptions? = null) = navigate(MAIN_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.mainScreen(
    navigateToAskNightStudy: () -> Unit,
    navigateToAddOuting: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            navigateToAskNightStudy = navigateToAskNightStudy,
            navigateToAddOuting = navigateToAddOuting,
            navigateToSetting = navigateToSetting,
            navigateToMyPoint = navigateToMyPoint,
            navigateToAddBus = navigateToAddBus,
            navigateToSchedule = navigateToSchedule,
            navigateToWakeUpSong = navigateToWakeUpSong,
            navigateToAddWakeUpSong = navigateToAddWakeUpSong,
        )
    }
}
