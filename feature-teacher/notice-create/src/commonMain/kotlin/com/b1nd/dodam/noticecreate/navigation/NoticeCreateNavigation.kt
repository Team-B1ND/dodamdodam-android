package com.b1nd.dodam.noticecreate.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.noticecreate.NoticeCreateScreen
import com.b1nd.dodam.ui.component.SnackbarState

const val NOTICE_CREATE_ROUTE = "notice_create"

fun NavController.navigateToNoticeCreate(navOptions: NavOptions? = null) = navigate(NOTICE_CREATE_ROUTE, navOptions)

fun NavGraphBuilder.noticeCreateScreen(popBackStack: () -> Unit, showSnackbar: (state: SnackbarState, message: String) -> Unit) {
    composable(NOTICE_CREATE_ROUTE) {
        NoticeCreateScreen(
            popBackStack = popBackStack,
            showSnackbar = showSnackbar,
        )
    }
}
