package com.b1nd.dodam.register.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.b1nd.dodam.data.core.model.Children
import com.b1nd.dodam.register.InfoScreen
import com.google.gson.Gson

const val INFO_ROUTE = "info"

fun NavController.navigateToInfo(
    childrenList: List<Children>? = null,
    navOptions: NavOptions? = navOptions {
        launchSingleTop = true
    },
) {
    val route = if (childrenList != null) {
        val jsonList = Gson().toJson(childrenList)
        "$INFO_ROUTE?childrenList=$jsonList"
    } else {
        INFO_ROUTE
    }

    navigate(route, navOptions)
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.infoScreen(
    onNextClick: (String, String, String, String, String, String, childrenList: List<Children>) -> Unit,
    onBackClick: () -> Unit,
    showToast: (String, String) -> Unit,
) {
    composable(
        route = "$INFO_ROUTE?childrenList={childrenList}",
        arguments = listOf(
            navArgument("childrenList") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) { backStackEntry: NavBackStackEntry ->
        val jsonList = backStackEntry.arguments?.getString("childrenList")
        val childrenList: List<Children> = if (jsonList != null) {
            Gson().fromJson(jsonList, Array<Children>::class.java).toList()
        } else {
            emptyList()
        }

        InfoScreen(
            onNextClick = onNextClick,
            onBackClick = onBackClick,
            childrenList = childrenList,
            showToast = showToast,
        )
    }
}
