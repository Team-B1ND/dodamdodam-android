package com.b1nd.dodam.teacher

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DodamTeacherApp() {
    val navHostController = rememberNavController()
    DodamTheme {
        NavHost(
            navController = navHostController,
            startDestination = ONBOARDING_ROUTE,
        ) {
            onboardingScreen(
                onRegisterClick = {},
                onLoginClick = navHostController::navigationToLogin,
            )
            loginScreen(
                onBackClick = navHostController::popBackStack,
                navigateToMain = {},
                role = "TEACHER",
            )
        }
    }
}
