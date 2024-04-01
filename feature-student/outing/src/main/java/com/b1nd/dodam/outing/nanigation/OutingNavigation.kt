package com.b1nd.dodam.outing.nanigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.outing.OutingScreen

const val OUTING_ROUTE = "outing"

fun NavController.navigateToOuting(navOptions: NavOptions? = null) = navigate(OUTING_ROUTE, navOptions)

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.outingScreen(onAddOutingClick: () -> Unit) {
    composable(
        route = OUTING_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        OutingScreen(onAddOutingClick)
    }
}
