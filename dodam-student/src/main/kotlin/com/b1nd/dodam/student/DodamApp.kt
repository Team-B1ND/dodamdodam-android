package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.meal.navigation.mealScreen
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo
import com.b1nd.dodam.student.main.navigation.mainScreen
import com.b1nd.dodam.student.main.navigation.navigateToMain

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = ONBOARDING_ROUTE,
        ) {
            onboardingScreen(
                onRegisterClick = { navController.navigateToInfo() },
                onLoginClick = { navController.navigationToLogin() },
            )
            mainScreen()
            infoScreen(
                onNextClick = { name, grade, room, number, email, phoneNumber ->
                    navController.navigateToAuth(
                        name = name,
                        grade = grade,
                        room = room,
                        number = number,
                        email = email,
                        phoneNumber = phoneNumber,
                    )
                },
                onBackClick = { navController.popBackStack() },
            )
            authScreen(
                onRegisterClick = { /* TODO: Navigation to Info Screen */ },
                onBackClick = { navController.popBackStack() },
            )
            loginScreen(
                onBackClick = { navController.popBackStack() },
                navigateToMain = { /* TODO: Login */ },
            )
            mealScreen()
        }
    }
}
