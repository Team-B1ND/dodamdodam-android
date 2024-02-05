package com.b1nd.dodam.student.main.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.student.main.MainScreen

const val MAIN_ROUTE = "main"

fun NavController.navigateToMain(navOptions: NavOptions) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen() {
    composable(route = MAIN_ROUTE) {
        MainScreen()
    }
}
