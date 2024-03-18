package com.b1nd.dodam.student.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.b1nd.dodam.student.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(navOptions: NavOptions? = null) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(onNightStudyAddClick: () -> Unit, onOutingAddClick: () -> Unit, onSleepOverAddClick: () -> Unit) {
    composable(route = MAIN_ROUTE) {
        MainScreen(
            onNightStudyAddClick = onNightStudyAddClick,
            onOutingAddClick = onOutingAddClick,
            onSleepOverClick = onSleepOverAddClick,
        )
    }
}
