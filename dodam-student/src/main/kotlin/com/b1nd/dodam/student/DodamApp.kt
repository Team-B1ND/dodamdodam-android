package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = ONBOARDING_ROUTE,
        ) {
            onboardingScreen(
                onRegisterClick = { /* TODO: Navigate to Register Screen */ },
                onLoginClick = { /* TODO: Navigate to Register Screen */ },
            )
        }
    }
}
