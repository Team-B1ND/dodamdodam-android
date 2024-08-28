package com.b1nd.dodam.student

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.b1nd.dodam.asknightstudy.navigation.askNightStudyScreen
import com.b1nd.dodam.asknightstudy.navigation.navigateToAskNightStudy
import com.b1nd.dodam.askout.navigation.askOutScreen
import com.b1nd.dodam.askout.navigation.navigateToAskOut
import com.b1nd.dodam.askwakeupsong.navigation.askWakeupSongScreen
import com.b1nd.dodam.askwakeupsong.navigation.navigateToAskWakeupSong
import com.b1nd.dodam.bus.navigation.busScreen
import com.b1nd.dodam.bus.navigation.navigateToBus
import com.b1nd.dodam.dds.component.DodamErrorToast
import com.b1nd.dodam.dds.component.DodamSuccessToast
import com.b1nd.dodam.dds.component.DodamWarningToast
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
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
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import login.navigation.loginScreen
import login.navigation.navigationToLogin

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun DodamApp(
    isLogin: Boolean,
    logout: () -> Unit,
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    firebaseAnalytics: FirebaseAnalytics,
    firebaseCrashlytics: FirebaseCrashlytics,
    scope: CoroutineScope = rememberCoroutineScope(),
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.route)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        firebaseCrashlytics.sendUnsentReports()
    }

    mainNavController.addOnDestinationChangedListener { _, destination, _ ->
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.route)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        firebaseCrashlytics.sendUnsentReports()
    }

    mainNavController.addOnDestinationChangedListener { _, destination, _ ->
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
            putString(FirebaseAnalytics.Param.SCREEN_CLASS, destination.route)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        firebaseCrashlytics.sendUnsentReports()
    }

    var state by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            Column {
                SnackbarHost(hostState = snackbarHostState) {
                    when (state) {
                        "SUCCESS" -> {
                            DodamSuccessToast(text = it.visuals.message)
                        }

                        "WARNING" -> {
                            DodamWarningToast(text = it.visuals.message)
                        }

                        "ERROR" -> {
                            DodamErrorToast(text = it.visuals.message)
                        }

                        else -> {}
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        },
    ) {
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
                navController = mainNavController,
                navigateToAskNightStudy = navController::navigateToAskNightStudy,
                navigateToAddOuting = navController::navigateToAskOut,
                navigateToSetting = navController::navigateToSetting,
                navigateToMyPoint = navController::navigateToPoint,
                navigateToAddBus = {
                    navController.navigateToBus()
                },
                navigateToSchedule = {
                    TODO("navigate to schedule screen")
                },
                navigateToWakeUpSong = {
                    navController.navigateToWakeupSong()
                },
                navigateToAddWakeUpSong = {
                    navController.navigateToAskWakeupSong()
                },
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
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
            wakeupSongScreen(
                onAddWakeupSongClick = {
                    navController.navigateToAskWakeupSong()
                },
                popBackStack = navController::popBackStack,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            askOutScreen(
                popBackStack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh", true)
                    navController.popBackStack()
                },
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            askNightStudyScreen(
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
                popBackStack = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("refresh", true)
                    navController.popBackStack()
                },
            )
            busScreen(
                popBackStack = navController::popBackStack,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            settingScreen(
                popBackStack = navController::popBackStack,
                logout = logout,
            )
            askWakeupSongScreen(
                popBackStack = navController::popBackStack,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            pointScreen(
                popBackStack = navController::popBackStack,
            )
        }
    }
}
