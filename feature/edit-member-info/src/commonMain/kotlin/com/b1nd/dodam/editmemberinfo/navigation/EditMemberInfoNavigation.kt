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
import net.thauvin.erik.urlencoder.UrlEncoderUtil

const val EDIT_MEMBER_INFO_ROUTE = "edit_member_info"

fun NavController.navigationToEditMemberInfo(
    profileImage: String?,
    name: String,
    phone: String,
    email: String,
    navOptions: NavOptions? = androidx.navigation.navOptions {
        launchSingleTop = true
    },
) {
    val image = if (profileImage.isNullOrEmpty()) {
        "default"
    } else {
        UrlEncoderUtil.encode(profileImage)
    }
    navigate("$EDIT_MEMBER_INFO_ROUTE/$image/$name/$email/$phone", navOptions)
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.editMemberInfoScreen(popBackStack: () -> Unit) {
    composable(
        route = "$EDIT_MEMBER_INFO_ROUTE/{image}/{name}/{email}/{phone}",
        arguments = listOf(
            navArgument("image") { type = NavType.StringType },
            navArgument("name") { type = NavType.StringType },
            navArgument("email") { type = NavType.StringType },
            navArgument("phone") { type = NavType.StringType },
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Down) },
    ) {
        val encodedImage = it.arguments?.getString("image") ?: "default"
        val profileImage = UrlEncoderUtil.decode(encodedImage)
        val name = it.arguments?.getString("name") ?: ""
        val email = it.arguments?.getString("email") ?: ""
        val phone = it.arguments?.getString("phone") ?: ""
        EditMemberInfoScreen(
            profileImage = profileImage,
            popBackStack = popBackStack,
            name = name,
            email = email,
            phone = phone,
        )
    }
}
