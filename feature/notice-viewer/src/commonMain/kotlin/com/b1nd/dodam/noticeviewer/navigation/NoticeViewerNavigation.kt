package com.b1nd.dodam.noticeviewer.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.data.notice.model.NoticeFile
import com.b1nd.dodam.noticeviewer.NoticeViewerScreen
import io.ktor.utils.io.core.toByteArray
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.serialization.json.Json

const val NOTICE_VIEWER_ROUTE = "notice_viewer"

fun NavController.navigateToNoticeViewer(startIndex: Int, images: String, navOptions: NavOptions? = null) =
    navigate("${NOTICE_VIEWER_ROUTE}/$startIndex/$images", navOptions)

@OptIn(ExperimentalEncodingApi::class)
fun NavGraphBuilder.noticeViewerScreen(popBackStack: () -> Unit) {
    composable(
        route = "${NOTICE_VIEWER_ROUTE}/{startIndex}/{images}",
        arguments = listOf(
            navArgument(
                name = "startIndex",
                {
                    type = NavType.IntType
                },
            ),
            navArgument(
                name = "images",
                {
                    type = NavType.StringType
                },
            ),
        ),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) { navBackStackEntry ->
        val imageArgument = navBackStackEntry.arguments?.getString("images") ?: ""

        val images = Json.decodeFromString<List<NoticeFile>>(
            Base64.UrlSafe.decode(imageArgument.toByteArray()).decodeToString(),
        )
        val startIndex = navBackStackEntry.arguments?.getInt("startIndex") ?: 0

        NoticeViewerScreen(
            popBackStack = popBackStack,
            images = images.toImmutableList(),
            startIndex = startIndex,
        )
    }
}
