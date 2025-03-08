package com.b1nd.dodam.notice.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.b1nd.dodam.notice.NoticeScreen
import io.ktor.utils.io.core.toByteArray
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

const val NOTICE_ROUTE = "notice"

fun NavController.navigateToNotice(
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
        restoreState = true
    },
) = navigate(NOTICE_ROUTE, navOptions)

@OptIn(ExperimentalEncodingApi::class)
fun NavGraphBuilder.noticeScreen(
    isTeacher: Boolean,
    changeBottomNavVisible: (visible: Boolean) -> Unit,
    navigateToNoticeCreate: (() -> Unit)?,
    navigateToNoticeViewer: (startIndex: Int, images: String) -> Unit,
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
            changeBottomNavVisible = changeBottomNavVisible,
            navigateToNoticeCreate = navigateToNoticeCreate,
            navigateToNoticeViewer = { startIndex, images ->
                navigateToNoticeViewer(
                    startIndex,
                    Base64.UrlSafe.encode(Json.encodeToString(images).encodeToByteArray())
                )
            }
        )
    }
}
