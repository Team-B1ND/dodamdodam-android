package com.b1nd.dodam.teacher

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.memory.MemoryCache
import coil3.network.CacheStrategy
import coil3.network.NetworkFetcher
import coil3.network.ktor.KtorNetworkFetcherFactory
import coil3.network.ktor.asNetworkClient
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.b1nd.dodam.designsystem.DodamTheme
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
import io.ktor.client.HttpClient

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun DodamTeacherApp() {
    setSingletonImageLoaderFactory { context ->
        getAsyncImageLoader(context)
    }
    val navHostController = rememberNavController()
    DodamTheme {
        NavHost(
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
    }
}

@OptIn(ExperimentalCoilApi::class)
internal fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context)
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