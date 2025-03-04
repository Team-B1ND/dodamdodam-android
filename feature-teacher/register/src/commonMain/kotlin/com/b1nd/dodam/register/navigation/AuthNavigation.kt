package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.b1nd.dodam.register.AuthScreen

const val AUTH_ROUTE = "auth"

fun NavController.navigateToAuth(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
    name: String,
    teacherRole: String,
    email: String,
    phoneNumber: String,
    extensionNumber: String,
) = navigate("$AUTH_ROUTE/$name/$teacherRole/$email/$phoneNumber/$extensionNumber", navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.authScreen(onRegisterClick: () -> Unit, onBackClick: () -> Unit) {
    composable(
        route = "$AUTH_ROUTE/{name}/{teacherRole}/{email}/{phoneNumber}/{extensionNumber}",
        arguments = listOf(
            navArgument("name") { NavType.StringType },
            navArgument("teacherRole") { NavType.StringType },
            navArgument("email") { NavType.StringType },
            navArgument("phoneNumber") { NavType.StringType },
            navArgument("extensionNumber") { NavType.StringType },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        AuthScreen(
            name = it.arguments?.getString("name") ?: "",
            teacherRole = it.arguments?.getString("name") ?: "",
            email = it.arguments?.getString("email") ?: "",
            phoneNumber = it.arguments?.getString("phoneNumber") ?: "",
            extensionNumber = it.arguments?.getString("extensionNumber") ?: "",
            navigateToMain = onRegisterClick,
            onBackClick = onBackClick,
        )
    }
}
