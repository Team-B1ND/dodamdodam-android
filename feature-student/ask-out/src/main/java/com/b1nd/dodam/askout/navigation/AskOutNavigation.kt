package com.b1nd.dodam.askout.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.askout.AskOutScreen

const val ASK_OUT_ROUTE = "ask_out"

fun NavController.navigateToAskOut(navOptions: NavOptions? = null) = navigate(ASK_OUT_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.askOutScreen(popBackStack: () -> Unit) {
    composable(
        route = ASK_OUT_ROUTE,
    ) {
        AskOutScreen(popBackStack = popBackStack)
    }
}