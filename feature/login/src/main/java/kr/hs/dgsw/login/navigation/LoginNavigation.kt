package kr.hs.dgsw.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kr.hs.dgsw.login.LoginScreen

const val LOGIN_ROUTE = "login"

fun NavController.navigationToLogin(navOptions: NavOptions?) = navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.onboardingScreen(onClickLogin: () -> Unit) {
    composable(route = LOGIN_ROUTE) {
        LoginScreen(

        )
    }
}