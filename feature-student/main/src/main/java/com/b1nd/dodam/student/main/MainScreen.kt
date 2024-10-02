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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.b1nd.dodam.designsystem.component.DodamNavigationBarItem
import com.b1nd.dodam.meal.navigation.mealScreen
import com.b1nd.dodam.member.navigation.allScreen
import com.b1nd.dodam.nightstudy.navigation.nightStudyScreen
import com.b1nd.dodam.outing.nanigation.outingScreen
import com.b1nd.dodam.student.home.navigation.HOME_ROUTE
import com.b1nd.dodam.student.home.navigation.homeScreen
import com.b1nd.dodam.student.main.navigation.MainDestination
import kotlinx.collections.immutable.toImmutableList

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun MainScreen(
    navController: NavHostController,
    navigateToAskNightStudy: () -> Unit,
    navigateToAddOuting: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToSchedule: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val mainScreenState = rememberMainScreenState(navController)

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
        ) {
            homeScreen(
                navigateToAskNightStudy = navigateToAskNightStudy,
                navigateToMeal = {
                    selectedIndex = 1
                    mainScreenState.navigateToMainDestination(MainDestination.MEAL)
                },
                navigateToNightStudy = {
                    selectedIndex = 3
                    mainScreenState.navigateToMainDestination(MainDestination.NIGHT_STUDY)
                },
                navigateToOut = {
                    selectedIndex = 2
                    mainScreenState.navigateToMainDestination(MainDestination.OUT)
                },
                navigateToAskOut = navigateToAddOuting,
                navigateToWakeupSongScreen = navigateToWakeUpSong,
                navigateToAskWakeupSongScreen = navigateToAddWakeUpSong,
            )
            mealScreen()
            nightStudyScreen(
                navigateToAskNightStudy,
                showToast,
                refresh,
                dispose,
            )
            outingScreen(
                navigateToAddOuting,
                showToast = showToast,
                refresh = refresh,
                dispose = dispose,
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
                .height(200.dp)
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
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

            com.b1nd.dodam.designsystem.component.DodamNavigationBar(
                items = mainScreenState.mainDestinations.map {
                    DodamNavigationBarItem(
                        selected = currentRoute == it.route,
                        icon = it.icon,
                        enable = currentRoute != it.route,
                        onClick = {
                            mainScreenState.navigateToMainDestination(it)
                        }
                    )
                }.toImmutableList(),
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
