package com.b1nd.dodam.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "onboarding"

fun NavController.navigateToOnboarding(navOptions: NavOptions? = NavOptions.Builder().setLaunchSingleTop(true).build()) = navigate(ONBOARDING_ROUTE, navOptions)

fun NavGraphBuilder.onboardingScreen(onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    composable(route = ONBOARDING_ROUTE) {
        OnboardingScreen(
            onRegisterClick = onRegisterClick,
            onLoginClick = onLoginClick,
        )
    }
}
