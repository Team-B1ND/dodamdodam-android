package com.b1nd.dodam.teacher

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamNavigationBar
import com.b1nd.dodam.designsystem.component.DodamNavigationBarItem
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.navigation.HOME_ROUTE
import com.b1nd.dodam.home.navigation.homeScreen
import com.b1nd.dodam.home.navigation.navigateToHome
import com.b1nd.dodam.login.navigation.loginScreen
import com.b1nd.dodam.login.navigation.navigationToLogin
import com.b1nd.dodam.onboarding.navigation.ONBOARDING_ROUTE
import com.b1nd.dodam.onboarding.navigation.navigateToOnboarding
import com.b1nd.dodam.onboarding.navigation.onboardingScreen
import com.b1nd.dodam.register.navigation.authScreen
import com.b1nd.dodam.register.navigation.infoScreen
import com.b1nd.dodam.register.navigation.navigateToAuth
import com.b1nd.dodam.register.navigation.navigateToInfo
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DodamTeacherApp() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    val navHostController = rememberNavController()
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    DodamTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navHostController,
                startDestination = ONBOARDING_ROUTE,
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
                )

                authScreen(
                    onRegisterClick = navHostController::navigateToOnboarding,
                    onBackClick = navHostController::popBackStack,
                )

                loginScreen(
                    onBackClick = navHostController::popBackStack,
                    navigateToMain = navHostController::navigateToHome,
                    role = "TEACHER",
                )

                homeScreen()
            }

            // Bottom Navigation
            DodamTeacherBottomNavigation(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(bottom = 8.dp),
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

@Composable
private fun DodamTeacherBottomNavigation(modifier: Modifier = Modifier, backStackEntry: NavBackStackEntry?, onClick: (destination: String) -> Unit) {
    val route = backStackEntry?.destination?.route

    if (route != null && route in listOf(HOME_ROUTE)) {
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
                    selected = route == "",
                    icon = DodamIcons.ForkAndKnife,
                    onClick = {},
                ),
                DodamNavigationBarItem(
                    selected = route == "",
                    icon = DodamIcons.DoorOpen,
                    onClick = {},
                ),
                DodamNavigationBarItem(
                    selected = route == "",
                    icon = DodamIcons.MoonPlus,
                    onClick = {},
                ),
                DodamNavigationBarItem(
                    selected = route == "",
                    icon = DodamIcons.Menu,
                    onClick = {},
                ),
            ),
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
