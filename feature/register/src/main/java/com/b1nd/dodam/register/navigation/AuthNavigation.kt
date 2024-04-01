package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.register.AuthScreen

const val AUTH_ROUTE = "auth"

fun NavController.navigateToAuth(
    navOptions: NavOptions? = null,
    name: String,
    grade: String,
    room: String,
    number: String,
    email: String,
    phoneNumber: String,
) = navigate("$AUTH_ROUTE/$name/$grade/$room/$number/$email/$phoneNumber", navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.authScreen(onRegisterClick: () -> Unit, onBackClick: () -> Unit) {
    composable(
        route = "$AUTH_ROUTE/{name}/{grade}/{room}/{number}/{email}/{phoneNumber}",
        arguments = listOf(
            navArgument("name") { NavType.StringType },
            navArgument("grade") { NavType.StringType },
            navArgument("room") { NavType.StringType },
            navArgument("number") { NavType.StringType },
            navArgument("email") { NavType.StringType },
            navArgument("phoneNumber") { NavType.StringType },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        AuthScreen(
            name = it.arguments?.getString("name") ?: "",
            grade = it.arguments?.getString("grade") ?: "",
            room = it.arguments?.getString("room") ?: "",
            number = it.arguments?.getString("number") ?: "",
            email = it.arguments?.getString("email") ?: "",
            phoneNumber = it.arguments?.getString("phoneNumber") ?: "",
            navigateToMain = onRegisterClick,
            onBackClick = onBackClick,
        )
    }
}
