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
import com.b1nd.dodam.club.navigation.clubScreen
import com.b1nd.dodam.club.navigation.myClubScreen
import com.b1nd.dodam.club.navigation.navigateToClub
import com.b1nd.dodam.club.navigation.navigateToMyClub
import com.b1nd.dodam.dds.component.DodamErrorToast
import com.b1nd.dodam.dds.component.DodamSuccessToast
import com.b1nd.dodam.dds.component.DodamWarningToast
import com.b1nd.dodam.editmemberinfo.navigation.editMemberInfoScreen
import com.b1nd.dodam.editmemberinfo.navigation.navigationToEditMemberInfo
import com.b1nd.dodam.group.navigation.groupScreen
import com.b1nd.dodam.group.navigation.navigateToGroup
import com.b1nd.dodam.groupadd.navigation.groupAddScreen
import com.b1nd.dodam.groupadd.navigation.navigateToGroupAdd
import com.b1nd.dodam.groupcreate.navigation.groupCreateScreen
import com.b1nd.dodam.groupcreate.navigation.navigateToGroupCreate
import com.b1nd.dodam.groupdetail.navigation.groupDetailScreen
import com.b1nd.dodam.groupdetail.navigation.navigateToGroupDetail
import com.b1nd.dodam.groupwaiting.navigation.groupWaitingScreen
import com.b1nd.dodam.groupwaiting.navigation.navigateToGroupWaiting
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.meal.navigation.mealScreen
import com.b1nd.dodam.meal.navigation.navigateToMeal
import com.b1nd.dodam.noticeviewer.navigation.navigateToNoticeViewer
import com.b1nd.dodam.noticeviewer.navigation.noticeViewerScreen
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.parent.childrenmanage.navigation.childrenManageScreen
import com.b1nd.dodam.parent.childrenmanage.navigation.navigateToChildrenManageScreen
import com.b1nd.dodam.parent.main.navigation.PARENT_MAIN_ROUTE
import com.b1nd.dodam.parent.main.navigation.navigateToParentMain
import com.b1nd.dodam.parent.main.navigation.parentMainScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo
import com.b1nd.dodam.register.navigation.navigateToSelectRole
import com.b1nd.dodam.register.navigation.selectRoleScreen
import com.b1nd.dodam.setting.navigation.navigateToSetting
import com.b1nd.dodam.setting.navigation.settingScreen
import com.b1nd.dodam.student.main.navigation.MAIN_ROUTE
import com.b1nd.dodam.student.main.navigation.mainScreen
import com.b1nd.dodam.student.main.navigation.navigateToMain
import com.b1nd.dodam.student.point.navigation.navigateToPoint
import com.b1nd.dodam.student.point.navigation.pointScreen
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.wakeupsong.navigation.navigateToWakeupSong
import com.b1nd.dodam.wakeupsong.navigation.wakeupSongScreen
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@Composable
fun DodamApp(
    logout: () -> Unit,
    navController: NavHostController = rememberNavController(),
    mainNavController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    firebaseAnalytics: FirebaseAnalytics,
    firebaseCrashlytics: FirebaseCrashlytics,
    scope: CoroutineScope = rememberCoroutineScope(),
    role: String,
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
    val showSnackbar: (SnackbarState, String) -> Unit = { snackBarState, message ->
        state = snackBarState.name
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

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
            startDestination = when (role) {
                "STUDENT" -> MAIN_ROUTE
                "PARENT" -> PARENT_MAIN_ROUTE
                "ADMIN" -> MAIN_ROUTE
                else -> ONBOARDING_ROUTE
            },
            enterTransition = { fadeIn(initialAlpha = 100f) },
            exitTransition = { fadeOut(targetAlpha = 100f) },
        ) {
            onboardingScreen(
                onRegisterClick = navController::navigateToSelectRole,
                onLoginClick = navController::navigationToLogin,
            )
            mainScreen(
                navController = mainNavController,
                navigateToMeal = navController::navigateToMeal,
                navigateToAskNightStudy = navController::navigateToAskNightStudy,
                navigateToAddOuting = navController::navigateToAskOut,
                navigateToSetting = navController::navigateToSetting,
                navigateToMyPoint = navController::navigateToPoint,
                navigateToClub = navController::navigateToClub,
                navigateToAddBus = {
                    navController.navigateToBus()
                },
                navigateToWakeUpSong = {
                    navController.navigateToWakeupSong()
                },
                navigateToAddWakeUpSong = {
                    navController.navigateToAskWakeupSong()
                },
                navigateToNoticeViewer = navController::navigateToNoticeViewer,
                navigateToGroup = navController::navigateToGroup,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
                role = role,
            )
            myClubScreen(
                showSnackbar = { status, text ->
                    state = status.toString()
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
                popBackStack = navController::popBackStack,
            )
            parentMainScreen(
                navController = mainNavController,
                navigateToMeal = navController::navigateToMeal,
                navigateToSetting = navController::navigateToSetting,
                navigateToNoticeViewer = navController::navigateToNoticeViewer,
                navigateToGroup = navController::navigateToGroup,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            mealScreen(
                popBackStack = navController::popBackStack,
            )
            selectRoleScreen(
                onBackClick = navController::popBackStack,
                navigateToChildrenManage = navController::navigateToChildrenManageScreen,
                navigateToInfo = { navController.navigateToInfo() },
            )
            infoScreen(
                onNextClick = { name, grade, room, number, email, phoneNumber, childrenList ->
                    navController.navigateToAuth(
                        name = name,
                        grade = grade,
                        room = room,
                        number = number,
                        email = email,
                        phoneNumber = phoneNumber,
                        childrenList = childrenList,
                    )
                },
                onBackClick = navController::popBackStack,
                showToast = { status, text ->
                    state = status
                    scope.launch { snackbarHostState.showSnackbar(text) }
                },
            )
            authScreen(
                onRegisterClick = {
                    navController.navigateToOnboarding()
                    state = "SUCCESS"
                    scope.launch {
                        snackbarHostState.showSnackbar("회원가입에 성공했습니다.")
                    }
                },
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
                navigateToParentMain = {
                    navController.navigateToParentMain(
                        navOptions {
                            popUpTo(ONBOARDING_ROUTE) {
                                inclusive = true
                            }
                        },
                    )
                },
                role = "STUDENT",
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
                versionInfo = "3.5.0",
                popBackStack = navController::popBackStack,
                logout = logout,
                navigationToEditMemberInfo = { profileImage, name, email, phone ->
                    navController.navigationToEditMemberInfo(
                        profileImage = profileImage,
                        name = name,
                        email = email,
                        phone = phone,
                    )
                },
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
            editMemberInfoScreen(
                popBackStack = navController::popBackStack,
            )
            clubScreen(
                popBackStack = navController::popBackStack,
                navigateToApply = navController::navigateToMyClub,
            )
            childrenManageScreen(
                popBackStack = navController::popBackStack,
                changeBottomNavVisible = { _ ->
                },
                navigateToInfo = { childrenList ->
                    navController.navigateToInfo(
                        childrenList,
                    )
                },
            )
            noticeViewerScreen(
                popBackStack = navController::popBackStack,
            )

            groupScreen(
                popBackStack = navController::popBackStack,
                isTeacher = true,
                navigateToGroupCreate = navController::navigateToGroupCreate,
                navigateToGroupDetail = { id ->
                    navController.navigateToGroupDetail(
                        id = id,
                    )
                },
            )

            groupDetailScreen(
                showSnackbar = showSnackbar,
                popBackStack = navController::popBackStack,
                navigateToGroupAdd = navController::navigateToGroupAdd,
                navigateToGroupWaiting = navController::navigateToGroupWaiting,
            )

            groupWaitingScreen(
                popBackStack = navController::popBackStack,
            )

            groupCreateScreen(
                popBackStack = navController::popBackStack,
                showSnackbar = showSnackbar,
            )

            groupAddScreen(
                showSnackbar = showSnackbar,
                popBackStack = navController::popBackStack,
            )
        }
    }
}
