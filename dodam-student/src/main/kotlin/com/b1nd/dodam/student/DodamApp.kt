package com.b1nd.dodam.student

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.b1nd.dodam.asknightstudy.navigation.askNightStudyScreen
import com.b1nd.dodam.asknightstudy.navigation.navigateToAskNightStudy
import com.b1nd.dodam.askout.navigation.askOutScreen
import com.b1nd.dodam.askout.navigation.navigateToAskOut
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
import com.b1nd.dodam.setting.navigation.navigateToSetting
import com.b1nd.dodam.setting.navigation.settingScreen
import com.b1nd.dodam.student.main.navigation.MAIN_ROUTE
import com.b1nd.dodam.student.main.navigation.mainScreen
import com.b1nd.dodam.student.main.navigation.navigateToMain
import com.b1nd.dodam.student.point.navigation.navigateToPoint
import com.b1nd.dodam.student.point.navigation.pointScreen
import com.b1nd.dodam.wakeupsong.navigation.navigateToWakeupSong
import com.b1nd.dodam.wakeupsong.navigation.wakeupSongScreen

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun DodamApp(isLogin: Boolean, deleteToken: () -> Unit, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = if (isLogin) MAIN_ROUTE else ONBOARDING_ROUTE,
        enterTransition = { fadeIn(initialAlpha = 100f) },
        exitTransition = { fadeOut(targetAlpha = 100f) },
    ) {
        onboardingScreen(
            onRegisterClick = navController::navigateToInfo,
            onLoginClick = navController::navigationToLogin,
        )
        mainScreen(
            navigateToAskNightStudy = navController::navigateToAskNightStudy,
            navigateToAddOuting = navController::navigateToAskOut,
            navigateToSetting = navController::navigateToSetting,
            navigateToMyPoint = navController::navigateToPoint,
            navigateToAddBus = {
                TODO("navigate to add add bus screen")
            },
            navigateToSchedule = {
                TODO("navigate to schedule screen")
            },
            navigateToWakeUpSong = {
                navController.navigateToWakeupSong()
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
        wakeupSongScreen(
            onAddWakeupSongClick = { TODO("navigate to add wakeup song screen") },
            popBackStack = navController::popBackStack,
        )
        askOutScreen(
            popBackStack = navController::popBackStack,
        )
        askNightStudyScreen(
            popBackStack = navController::popBackStack,
        )
        settingScreen(
            popBackStack = navController::popBackStack,
            logout = {
                deleteToken()
                navController.navigateToOnboarding(
                    navOptions {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    },
                )
            },
        )
        pointScreen(
            popBackStack = navController::popBackStack,
        )
    }
}
