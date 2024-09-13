package com.b1nd.dodam.point.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.point.PointScreen

const val POINT_ROUTE = "point"

fun NavController.navigateToPoint(navOptions: NavOptions? = null) =
    this.navigate(POINT_ROUTE)

fun NavGraphBuilder.pointScreen() {
    composable(POINT_ROUTE) {
        PointScreen()
    }
}