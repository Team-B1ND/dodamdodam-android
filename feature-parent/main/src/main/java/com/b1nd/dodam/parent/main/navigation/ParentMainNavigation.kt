package com.b1nd.dodam.parent.main.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.parent.main.ParentMainScreen

const val PARENT_MAIN_ROUTE = "parent_main"

fun NavController.navigateToParentMain(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) = navigate(PARENT_MAIN_ROUTE, navOptions)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
fun NavGraphBuilder.parentMainScreen(
    navController: NavHostController,
    navigateToMeal: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToNoticeViewer: (startIndex: Int, images: String) -> Unit,
    navigateToGroup: () -> Unit,
    showToast: (String, String) -> Unit,
) {
    composable(route = PARENT_MAIN_ROUTE) {
        ParentMainScreen(
            navigateToMeal = navigateToMeal,
            navController = navController,
            navigateToSetting = navigateToSetting,
            navigateToNoticeViewer = navigateToNoticeViewer,
            navigateToGroup = navigateToGroup,
            showToast = showToast,
        )
    }
}
