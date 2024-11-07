package com.b1nd.dodam.editmemberinfo.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.b1nd.dodam.editmemberinfo.EditMemberInfoScreen
import io.ktor.http.encodeURLPath
import io.ktor.util.decodeBase64String
import io.ktor.util.encodeBase64
import io.ktor.utils.io.core.toByteArray
import kotlinx.io.bytestring.encodeToByteString
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

const val EDIT_MEMBER_INFO_ROUTE = "login"

fun NavController.navigationToEditMemberInfo(
    profileImage: String?,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) {
    val image = (profileImage ?: "default").toByteArray().encodeBase64()
    navigate("$EDIT_MEMBER_INFO_ROUTE/$image", navOptions)
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.editMemberInfoScreen(popBackStack: () -> Unit) {
    composable(
        route = "$EDIT_MEMBER_INFO_ROUTE/{image}",
        arguments = listOf(
            navArgument("image") { type = NavType.StringType }
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        val encodedImage = it.arguments?.getString("image") ?: "default"
        val profileImage = encodedImage.decodeBase64String()
        EditMemberInfoScreen(
            profileImage = profileImage,
            popBackStack = popBackStack
        )
    }
}