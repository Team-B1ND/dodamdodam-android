package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import kr.hs.dgsw.register.navigation.authScreen
import kr.hs.dgsw.register.navigation.infoScreen
import kr.hs.dgsw.register.navigation.navigateToAuth
import kr.hs.dgsw.register.navigation.navigateToInfo

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
