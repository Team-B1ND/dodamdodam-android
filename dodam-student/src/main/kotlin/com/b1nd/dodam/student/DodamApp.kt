package com.b1nd.dodam.student

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.b1nd.dodam.ask_out.navigation.askOutScreen
import com.b1nd.dodam.ask_out.navigation.navigateToAskOut
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.nightstudy.navigation.nightStudyScreen
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.outing.nanigation.outingScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo
import com.b1nd.dodam.student.main.navigation.MAIN_ROUTE
import com.b1nd.dodam.student.main.navigation.mainScreen
import com.b1nd.dodam.student.main.navigation.navigateToMain

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun DodamApp(isLogin: Boolean, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = if (isLogin) MAIN_ROUTE else ONBOARDING_ROUTE,
    ) {
        onboardingScreen(
            onRegisterClick = navController::navigateToInfo,
            onLoginClick = navController::navigationToLogin,
        )
        mainScreen(
            navigateToAskNightStudy = {
                TODO("navigate to add nightStudy screen")
            },
            navigateToAddOuting = navController::navigateToAskOut,
            navigateToSetting = {
                TODO("navigate to setting screen")
            },
            navigateToMyPoint = {
                TODO("navigate to add my point screen")
            },
            navigateToAddBus = {
                TODO("navigate to add add bus screen")
            },
            navigateToSchedule = {
                TODO("navigate to schedule screen")
            },
            navigateToWakeUpSong = {
                TODO("navigate to wake up song screen")
            },
            navigateToAddWakeUpSong = {
                TODO("navigate to add wake up song screen")
            },
        )
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
            onBackClick = navController::popBackStack,
        )
        authScreen(
            onRegisterClick = navController::navigateToOnboarding,
            onBackClick = navController::popBackStack,
        )
        loginScreen(
            onBackClick = navController::popBackStack,
            navigateToMain = {
                navController.navigateToMain(
                    navOptions {
                        popUpTo(ONBOARDING_ROUTE) {
                            inclusive = true
                        }
                    },
                )
            },
        )
        nightStudyScreen(
            onAddClick = { TODO("navigate to add nightStudy screen") },
        )
        outingScreen(
            onAddOutingClick = { TODO("navigate to add outing screen") },
        )
        askOutScreen(
            popBackStack = navController::popBackStack
        )
    }
}
