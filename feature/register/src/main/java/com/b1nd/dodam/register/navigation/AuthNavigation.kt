package com.b1nd.dodam.register.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val AUTH_ROUTE = "auth"

fun NavController.navigateToAuth(navOptions: NavOptions?) = navigate(AUTH_ROUTE, navOptions)

fun NavGraphBuilder.authScreen(onRegisterClick: () -> Unit, onBackClick: () -> Unit) {
    composable(route = AUTH_ROUTE) {
        authScreen(
            onRegisterClick = onRegisterClick,
            onBackClick = onBackClick,
        )
    }
}
