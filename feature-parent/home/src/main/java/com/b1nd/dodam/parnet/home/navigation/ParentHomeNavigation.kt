package com.b1nd.dodam.parnet.home.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.parnet.home.ParentHomeScreen


const val PARENT_HOME_ROUTE = "parent_home"

fun NavController.navigateToParentHome(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(PARENT_HOME_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.parentHomeScreen(
    navigateToMeal: () -> Unit,
) {
    composable(
        route = PARENT_HOME_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        ParentHomeScreen(
            navigateToMeal = navigateToMeal,
        )
    }
}
