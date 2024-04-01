package com.b1nd.dodam.nightstudy.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.nightstudy.NightStudyScreen

const val NIGHT_STUDY_ROUTE = "nightstudy"

fun NavController.navigateToNightStudy(navOptions: NavOptions? = null) = navigate(NIGHT_STUDY_ROUTE, navOptions)

@ExperimentalMaterial3Api
fun NavGraphBuilder.nightStudyScreen(onAddClick: () -> Unit) {
    composable(
        route = NIGHT_STUDY_ROUTE,
    ) {
        NightStudyScreen(onAddClick)
    }
}
