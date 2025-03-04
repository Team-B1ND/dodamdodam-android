package com.b1nd.dodam.register.navigation

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.b1nd.dodam.data.core.model.Children
import com.b1nd.dodam.register.AuthScreen
import com.google.gson.Gson

const val AUTH_ROUTE = "auth"

fun NavController.navigateToAuth(
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
    name: String,
    grade: String,
    room: String,
    number: String,
    email: String,
    phoneNumber: String,
    childrenList: List<Children>
) {
    val route = if (childrenList.isNotEmpty()) {
        val jsonList = Gson().toJson(childrenList)
        "$AUTH_ROUTE/$name/$phoneNumber/?childrenList=$jsonList"
    } else {
        "$AUTH_ROUTE/$name/$grade/$room/$number/$email/$phoneNumber"
    }
    navigate(route, navOptions)
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.authScreen(onRegisterClick: () -> Unit, onBackClick: () -> Unit) {
    composable(
        route = "$AUTH_ROUTE/{name}/{grade}/{room}/{number}/{email}/{phoneNumber}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("grade") { type = NavType.StringType },
            navArgument("room") { type = NavType.StringType },
            navArgument("number") { type = NavType.StringType },
            navArgument("email") { type = NavType.StringType },
            navArgument("phoneNumber") { type = NavType.StringType },
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
            childrenList = emptyList(),
            navigateToMain = onRegisterClick,
            onBackClick = onBackClick
        )
    }

    composable(
        route = "$AUTH_ROUTE/{name}/{phoneNumber}/?childrenList={childrenList}",
        arguments = listOf(
            navArgument("name") { type = NavType.StringType },
            navArgument("phoneNumber") { type = NavType.StringType },
            navArgument("childrenList") { type = NavType.StringType },
        )
    ) {
        val jsonList = it.arguments?.getString("childrenList")
        val childrenList = Gson().fromJson(jsonList, Array<Children>::class.java).toList()

        AuthScreen(
            name = it.arguments?.getString("name") ?: "",
            grade = "",
            room = "",
            number = "",
            email = "",
            phoneNumber = it.arguments?.getString("phoneNumber") ?: "",
            childrenList = childrenList,
            navigateToMain = onRegisterClick,
            onBackClick = onBackClick,
        )
    }
}