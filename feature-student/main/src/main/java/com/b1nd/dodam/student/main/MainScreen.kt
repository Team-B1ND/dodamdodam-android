package com.b1nd.dodam.student.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.dds.component.DodamNavigationBar
import com.b1nd.dodam.dds.component.rememberDodamNavigationItem
import com.b1nd.dodam.dds.foundation.DodamIcons
import com.b1nd.dodam.meal.navigation.MEAL_ROUTE
import com.b1nd.dodam.meal.navigation.mealScreen
import com.b1nd.dodam.member.navigation.ALL_ROUTE
import com.b1nd.dodam.member.navigation.allScreen
import com.b1nd.dodam.nightstudy.navigation.NIGHT_STUDY_ROUTE
import com.b1nd.dodam.nightstudy.navigation.navigateToNightStudy
import com.b1nd.dodam.nightstudy.navigation.nightStudyScreen
import com.b1nd.dodam.outing.nanigation.OUTING_ROUTE
import com.b1nd.dodam.outing.nanigation.outingScreen
import com.b1nd.dodam.student.home.navigation.HOME_ROUTE
import com.b1nd.dodam.student.home.navigation.homeScreen
import kotlinx.collections.immutable.persistentListOf

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun MainScreen(
    navController: NavHostController = rememberNavController(),
    navigateToAskNightStudy: () -> Unit,
    navigateToAddOuting: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
) {
    val navItems = persistentListOf(
        rememberDodamNavigationItem(HOME_ROUTE, DodamIcons.Home),
        rememberDodamNavigationItem(MEAL_ROUTE, DodamIcons.ForkAndKnife),
        rememberDodamNavigationItem(OUTING_ROUTE, DodamIcons.DoorOpen),
        rememberDodamNavigationItem(NIGHT_STUDY_ROUTE, DodamIcons.MoonPlus),
        rememberDodamNavigationItem(ALL_ROUTE, DodamIcons.Menu),
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
        ) {
            homeScreen(
                navigateToAskNightStudy = navigateToAskNightStudy,
                navigateToMeal = { /*navController::navigationToMeal*/ },
                navigateToNightStudy = { /*navController::navigateToNightStudy*/ },
                navigateToOut = { /*navController::navigateToOuting*/ },
                navigateToAskOut = navigateToAddOuting,
                navigateToWakeupSongScreen = navigateToWakeUpSong,
                navigateToAskWakeupSongScreen = navigateToAddWakeUpSong,
            )
            mealScreen()
            nightStudyScreen(navigateToAskNightStudy)
            outingScreen(
                navigateToAddOuting,
            )
            allScreen(
                navigateToSetting,
                navigateToMyPoint,
                navigateToAddBus,
                navigateToAskNightStudy,
                navigateToAddOuting,
                navigateToSchedule,
                navigateToWakeUpSong,
                navigateToAddWakeUpSong,
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                            MaterialTheme.colorScheme.surface,
                        ),
                    ),
                ),
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
                .align(Alignment.BottomCenter),
        ) {
            DodamNavigationBar(
                navigationItems = navItems,
            ) { item ->
                navController.navigate(item.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
