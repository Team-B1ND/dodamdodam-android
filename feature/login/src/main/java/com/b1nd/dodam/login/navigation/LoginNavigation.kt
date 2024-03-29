package com.b1nd.dodam.login.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.login.LoginScreen

const val LOGIN_ROUTE = "login"

fun NavController.navigationToLogin(navOptions: NavOptions? = null) = navigate(LOGIN_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.loginScreen(onBackClick: () -> Unit, navigateToMain: () -> Unit) {
    composable(
        route = LOGIN_ROUTE,
    ) {
        LoginScreen(
            onBackClick = onBackClick,
            navigateToMain = navigateToMain,
        )
    }
}
