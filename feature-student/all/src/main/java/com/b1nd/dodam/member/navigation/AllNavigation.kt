package com.b1nd.dodam.member.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.member.AllScreen

const val ALL_ROUTE = "all"

fun NavController.navigateToAllScreen(navOptions: NavOptions? = null) =
    navigate(ALL_ROUTE, navOptions)

fun NavGraphBuilder.allScreen(
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToAddNightStudy: () -> Unit,
    navigateToAddOutingStudy: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit
) {
    composable(
        route = ALL_ROUTE,
    ) {
        AllScreen(
            navigateToSetting = navigateToSetting,
            navigateToMyPoint = navigateToMyPoint,
            navigateToAddBus = navigateToAddBus,
            navigateToAddNightStudy = navigateToAddNightStudy,
            navigateToAddOutingStudy = navigateToAddOutingStudy,
            navigateToSchedule = navigateToSchedule,
            navigateToWakeUpSong = navigateToWakeUpSong,
            navigateToAddWakeUpSong = navigateToAddWakeUpSong
        )
    }
}
