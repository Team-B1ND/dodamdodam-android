package com.b1nd.dodam.teacher

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.b1nd.dodam.all.navigation.ALL_ROUTE
import com.b1nd.dodam.all.navigation.allScreen
import com.b1nd.dodam.approvenightstudy.navigation.approveNightStudyScreen
import com.b1nd.dodam.approvenightstudy.navigation.navigateToApproveNightStudy
import com.b1nd.dodam.approveouting.approveOutingScreen
import com.b1nd.dodam.approveouting.navigateToApproveOuting
import com.b1nd.dodam.club.navigation.clubScreen
import com.b1nd.dodam.club.navigation.navigateToClub
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamDialog
import com.b1nd.dodam.designsystem.component.DodamNavigationBar
import com.b1nd.dodam.designsystem.component.DodamNavigationBarItem
import com.b1nd.dodam.designsystem.foundation.DodamIcons
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
import com.b1nd.dodam.home.navigation.HOME_ROUTE
import com.b1nd.dodam.home.navigation.homeScreen
import com.b1nd.dodam.home.navigation.navigateToHome
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.meal.navigation.mealScreen
import com.b1nd.dodam.meal.navigation.navigateToMeal
import com.b1nd.dodam.nightstudy.navigation.NIGHT_STUDY_ROUTE
import com.b1nd.dodam.nightstudy.navigation.nightStudyScreen
import com.b1nd.dodam.notice.navigation.NOTICE_ROUTE
import com.b1nd.dodam.notice.navigation.noticeScreen
import com.b1nd.dodam.noticecreate.navigation.navigateToNoticeCreate
import com.b1nd.dodam.noticecreate.navigation.noticeCreateScreen
import com.b1nd.dodam.noticeviewer.navigation.navigateToNoticeViewer
import com.b1nd.dodam.noticeviewer.navigation.noticeViewerScreen
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.outing.navigation.OUTING_ROUTE
import com.b1nd.dodam.outing.navigation.outingScreen
import com.b1nd.dodam.point.navigation.navigateToPoint
import com.b1nd.dodam.point.navigation.pointScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo
import com.b1nd.dodam.setting.navigation.navigateToSetting
import com.b1nd.dodam.setting.navigation.settingScreen
import com.b1nd.dodam.ui.component.DodamSnackbar
import com.b1nd.dodam.ui.component.SnackbarState
import com.b1nd.dodam.ui.icons.B1NDLogo
import com.b1nd.dodam.ui.icons.DodamLogo
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

const val VERSION_INFO = "3.2.3"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class, KoinExperimentalAPI::class)
@Composable
fun DodamTeacherApp(exit: () -> Unit, viewModel: DodamTeacherAppViewModel = koinViewModel()) {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }

    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val isLogin by viewModel.isLoginState.collectAsState()
    val bundleData by viewModel.bundleModel.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    var snackbarState: SnackbarState = remember { SnackbarState.SUCCESS }
    var showVersionDialog by remember { mutableStateOf(false) }
    var showBottomNavVisible by remember { mutableStateOf(true) }
    var platform by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.loadToken()
        platform = getPlatformName()
    }

    LaunchedEffect(platform) {
        if (platform == PlatformModel.IOS.name) {
            viewModel.getBundleId()
        }
    }

    LaunchedEffect(bundleData.bundleId) {
        if (bundleData.bundleId?.isNotEmpty() == true && bundleData.bundleId != VERSION_INFO) {
            showVersionDialog = true
        }
    }

    val showSnackbar: (state: SnackbarState, message: String) -> Unit = { state, message ->
        // ui 스레드일지 알 수 없음
        coroutineScope.launch {
            snackbarState = state
            snackbarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
            )
        }
    }

    DodamTheme {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) {
                    DodamSnackbar(
                        state = snackbarState,
                        text = it.visuals.message,
                        showDismissAction = it.visuals.withDismissAction,
                        onDismissRequest = {
                            it.dismiss()
                        },
                    )
                }
            },
        ) {
            if (showVersionDialog) {
                Dialog(
                    onDismissRequest = {
                        showVersionDialog = false
                    },
                ) {
                    DodamDialog(
                        confirmButton = {
                            showVersionDialog = false
                        },
                        title = "최신 버전의 앱이 있습니다.",
                        body = "최적의 사용 환경을 위해 최신 버전의\n앱으로 업데이트해주세요",
                    )
                }
            }

            if (isLogin == null) {
                LunchScreen()
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navHostController,
                        startDestination = if (isLogin!!) HOME_ROUTE else ONBOARDING_ROUTE,
                    ) {
                        onboardingScreen(
                            onRegisterClick = navHostController::navigateToInfo,
                            onLoginClick = navHostController::navigationToLogin,
                        )

                        infoScreen(
                            onNextClick = { name, teacherRole, email, phoneNumber, extensionNumber ->
                                navHostController.navigateToAuth(
                                    name = name,
                                    teacherRole = teacherRole,
                                    email = email,
                                    phoneNumber = phoneNumber,
                                    extensionNumber = extensionNumber,
                                )
                            },
                            onBackClick = navHostController::popBackStack,
                            showSnackbar = showSnackbar,
                        )

                        authScreen(
                            onRegisterClick = navHostController::navigateToOnboarding,
                            onBackClick = navHostController::popBackStack,
                        )

                        loginScreen(
                            onBackClick = navHostController::popBackStack,
                            navigateToMain = navHostController::navigateToHome,
                            navigateToParentMain = {},
                            role = "TEACHER",
                        )

                        nightStudyScreen(
                            navigateToApproveStudy = navHostController::navigateToApproveNightStudy,
                        )
                        homeScreen(
                            navigateToMeal = {
                                navHostController.navigateToMeal(
                                    navOptions = navOptions {
                                        popUpTo(navHostController.graph.findStartDestination().route.toString()) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    },
                                )
                            },
                            navigateToOut = {
                                navHostController.navigateToApproveOuting(
                                    title = 0,
                                )
                            },
                            navigateToSleep = {
                                navHostController.navigateToApproveOuting(
                                    title = 1,
                                )
                            },
                            navigateToNightStudy = navHostController::navigateToApproveNightStudy,
                        )

                        mealScreen(
                            popBackStack = navHostController::popBackStack,
                        )

                        pointScreen(
                            showSnackbar = showSnackbar,
                            popBackStack = navHostController::popBackStack,
                        )

                        outingScreen(
                            navigateToApprove = { title ->
                                navHostController.navigateToApproveOuting(
                                    title = title,
                                )
                            },
                        )
                        approveOutingScreen(
                            onBackClick = navHostController::popBackStack,
                            showSnackbar = showSnackbar,
                        )

                        allScreen(
                            navigateToSetting = navHostController::navigateToSetting,
                            navigateToOut = navHostController::navigateToApproveOuting,
                            navigateToNightStudy = navHostController::navigateToApproveNightStudy,
                            navigateToPoint = navHostController::navigateToPoint,
                            navigateToGroup = navHostController::navigateToGroup,
                            navigateToClub = navHostController::navigateToClub,
                        )

                        approveNightStudyScreen(
                            onBackClick = navHostController::popBackStack,
                            showSnackbar = showSnackbar,
                        )

                        settingScreen(
                            popBackStack = navHostController::popBackStack,
                            logout = exit,
                            versionInfo = VERSION_INFO,
                            navigationToEditMemberInfo = { profileImage, name, email, phone ->
                                navHostController.navigationToEditMemberInfo(
                                    profileImage = profileImage,
                                    name = name,
                                    email = email,
                                    phone = phone,
                                )
                            },
                        )

                        editMemberInfoScreen(
                            popBackStack = navHostController::popBackStack,
                        )
                        noticeScreen(
                            isTeacher = true,
                            navigateToNoticeCreate = navHostController::navigateToNoticeCreate,
                            changeBottomNavVisible = { visible ->
                                showBottomNavVisible = visible
                            },
                            navigateToNoticeViewer = navHostController::navigateToNoticeViewer,
                        )

                        noticeCreateScreen(
                            popBackStack = navHostController::popBackStack,
                            showSnackbar = showSnackbar,
                        )

                        groupScreen(
                            popBackStack = navHostController::popBackStack,
                            isTeacher = true,
                            navigateToGroupCreate = navHostController::navigateToGroupCreate,
                            navigateToGroupDetail = { id ->
                                navHostController.navigateToGroupDetail(
                                    id = id,
                                )
                            },
                        )

                        groupDetailScreen(
                            showSnackbar = showSnackbar,
                            popBackStack = navHostController::popBackStack,
                            navigateToGroupAdd = navHostController::navigateToGroupAdd,
                            navigateToGroupWaiting = navHostController::navigateToGroupWaiting,
                        )

                        groupWaitingScreen(
                            popBackStack = navHostController::popBackStack,
                        )

                        groupCreateScreen(
                            popBackStack = navHostController::popBackStack,
                            showSnackbar = showSnackbar,
                        )

                        groupAddScreen(
                            showSnackbar = showSnackbar,
                            popBackStack = navHostController::popBackStack,
                        )

                        clubScreen(
                            showSnackbar = showSnackbar,
                            popBackStack = navHostController::popBackStack,
                        )

                        noticeViewerScreen(
                            popBackStack = navHostController::popBackStack,
                        )
                    }

                    // Bottom Navigation
                    DodamTeacherBottomNavigation(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                            ),
                        visible = showBottomNavVisible,
                        backStackEntry = backStackEntry,
                        onClick = { destination ->
                            navHostController.navigate(
                                route = destination,
                            ) {
                                popUpTo(navHostController.graph.findStartDestination().route.toString()) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }
            }
        }
    }
}

expect fun getPlatformName(): String

@Composable
private fun DodamTeacherBottomNavigation(
    modifier: Modifier = Modifier,
    backStackEntry: NavBackStackEntry?,
    visible: Boolean,
    onClick: (destination: String) -> Unit,
) {
    val route = backStackEntry?.destination?.route

    if (visible && route != null && route in listOf(
            HOME_ROUTE,
            NOTICE_ROUTE,
            NIGHT_STUDY_ROUTE,
            ALL_ROUTE,
            OUTING_ROUTE,
        )
    ) {
        DodamNavigationBar(
            modifier = modifier,
            items = persistentListOf(
                DodamNavigationBarItem(
                    selected = route == HOME_ROUTE,
                    icon = DodamIcons.Home,
                    onClick = {
                        onClick(HOME_ROUTE)
                    },
                    enable = route != HOME_ROUTE,
                ),
                DodamNavigationBarItem(
                    selected = route == NOTICE_ROUTE,
                    icon = DodamIcons.Bell,
                    onClick = {
                        onClick(NOTICE_ROUTE)
                    },
                    enable = route != NOTICE_ROUTE,
                ),
                DodamNavigationBarItem(
                    selected = route == OUTING_ROUTE,
                    icon = DodamIcons.DoorOpen,
                    onClick = {
                        onClick(OUTING_ROUTE)
                    },
                    enable = route != OUTING_ROUTE,
                ),
                DodamNavigationBarItem(
                    selected = route == NIGHT_STUDY_ROUTE,
                    icon = DodamIcons.MoonPlus,
                    onClick = {
                        onClick(NIGHT_STUDY_ROUTE)
                    },
                    enable = route != NIGHT_STUDY_ROUTE,
                ),
                DodamNavigationBarItem(
                    selected = route == ALL_ROUTE,
                    icon = DodamIcons.Menu,
                    onClick = {
                        onClick(ALL_ROUTE)
                    },
                    enable = route != ALL_ROUTE,
                ),
            ),
        )
    }
}

@Composable
private fun LunchScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DodamTheme.colors.backgroundNeutral),
    ) {
        Image(
            modifier = Modifier.align(Alignment.Center),
            imageVector = DodamLogo,
            colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
            contentDescription = null,
        )
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(60.dp)
                .height(16.dp)
                .navigationBarsPadding(),
            imageVector = B1NDLogo,
            colorFilter = ColorFilter.tint(DodamTheme.colors.primaryNormal),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
internal fun getAsyncImageLoader(context: PlatformContext) = ImageLoader.Builder(context)
    .crossfade(true)
    .memoryCache {
        MemoryCache.Builder()
            .maxSizePercent(context, percent = 0.25)
            .build()
    }
    .logger(DebugLogger())
    .components {
        add(KtorNetworkFetcherFactory())
    }
    .build()
