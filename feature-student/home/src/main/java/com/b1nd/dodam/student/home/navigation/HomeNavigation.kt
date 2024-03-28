package com.b1nd.dodam.student.home.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.student.home.HomeScreen

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.homeScreen() {
    composable(
        route = HOME_ROUTE,
    ) {
        HomeScreen()
    }
}
