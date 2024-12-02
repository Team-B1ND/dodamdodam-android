package com.b1nd.dodam.notice.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.notice.NoticeScreen

const val NOTICE_ROUTE = "notice"

fun NavController.navigateToNotice(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
        restoreState = true
    },
) = navigate(NOTICE_ROUTE, navOptions)

fun NavGraphBuilder.noticeScreen(
    isTeacher: Boolean,
    navigateToNoticeCreate: (() -> Unit)?
) {
    composable(
        route = NOTICE_ROUTE,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        NoticeScreen(
            isTeacher = isTeacher,
            navigateToNoticeCreate = navigateToNoticeCreate
        )
    }
}
