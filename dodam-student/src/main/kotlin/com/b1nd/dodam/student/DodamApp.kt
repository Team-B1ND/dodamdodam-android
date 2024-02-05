package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = ONBOARDING_ROUTE,
        ) {
            onboardingScreen(
                onRegisterClick = { navController.navigateToInfo(null) },
                onLoginClick = { /* TODO: Navigation to Login Screen */ },
            )
            infoScreen(
                onNextClick = { navController.navigateToAuth(null) },
                onBackClick = { navController.popBackStack() },
            )
            authScreen(
                onRegisterClick = { /* TODO: Navigation to Info Screen */ },
                onBackClick = { navController.popBackStack() },
            )
        }
    }
}
