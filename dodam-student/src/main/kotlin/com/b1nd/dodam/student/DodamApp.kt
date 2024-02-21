package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = ONBOARDING_ROUTE,
        ) {
            onboardingScreen(
                onRegisterClick = { /* TODO: Navigate to Register Screen */ },
                onLoginClick = { navController.navigationToLogin(navOptions = null) },
            )
            loginScreen(
                onBackClick = { navController.popBackStack() },
                navigateToMain = { /* TODO: Login */ },
            )
        }
    }
}
