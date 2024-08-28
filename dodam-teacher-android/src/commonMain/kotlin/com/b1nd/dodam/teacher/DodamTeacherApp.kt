package com.b1nd.dodam.teacher

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo

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
                onRegisterClick = navHostController::navigateToInfo,
                onLoginClick = {},
            )

            infoScreen(
                onNextClick = { name, teacherRole, email, phoneNumber, extensionNumber ->
                    navHostController.navigateToAuth(
                        name = name,
                        teacherRole = teacherRole,
                        email = email,
                        phoneNumber = phoneNumber,
                        extensionNumber = extensionNumber
                    )
                },
                onBackClick = navHostController::popBackStack
            )

            authScreen(
                onRegisterClick = navHostController::navigateToOnboarding,
                onBackClick = navHostController::popBackStack
            )
        }
    }
}
