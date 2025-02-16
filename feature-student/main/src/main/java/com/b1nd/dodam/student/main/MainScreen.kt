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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import com.b1nd.dodam.designsystem.component.DodamNavigationBar
import com.b1nd.dodam.designsystem.component.DodamNavigationBarItem
import com.b1nd.dodam.meal.navigation.navigateToMeal
import com.b1nd.dodam.member.navigation.allScreen
import com.b1nd.dodam.nightstudy.navigation.nightStudyScreen
import com.b1nd.dodam.notice.navigation.noticeScreen
import com.b1nd.dodam.outing.nanigation.navigateToOuting
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
    navigateToMeal: () -> Unit,
    navigateToAskNightStudy: () -> Unit,
    navigateToAddOuting: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToMyPoint: () -> Unit,
    navigateToAddBus: () -> Unit,
    navigateToWakeUpSong: () -> Unit,
    navigateToAddWakeUpSong: () -> Unit,
    showToast: (String, String) -> Unit,
    refresh: () -> Boolean,
    dispose: () -> Unit,
    role: String,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val mainScreenState = rememberMainScreenState(navController)
    var bottomNavVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE,
        ) {
            homeScreen(
                navigateToAskNightStudy = navigateToAskNightStudy,
                navigateToMeal = navigateToMeal,
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
            noticeScreen(
                isTeacher = false,
                navigateToNoticeCreate = null,
                changeBottomNavVisible = { visible ->
                    bottomNavVisible = visible
                }
            )
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
                navigateToSetting = navigateToSetting,
                navigateToMyPoint = navigateToMyPoint,
                navigateToAddBus = navigateToAddBus,
                navigateToOuting = {
                    navController.navigateToOuting(
                        navOptions = navOptions {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        },
                    )
                },
                navigateToWakeUpSong = navigateToWakeUpSong,
                navigateToAddWakeUpSong = navigateToAddWakeUpSong,
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
        if (bottomNavVisible) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .navigationBarsPadding()
                    .align(Alignment.BottomCenter),
            ) {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                DodamNavigationBar(
                    items = mainScreenState.mainDestinations.map {
                        DodamNavigationBarItem(
                            selected = currentRoute == it.route,
                            icon = it.icon,
                            enable = currentRoute != it.route,
                            onClick = {
                                mainScreenState.navigateToMainDestination(it)
                            },
                        )
                    }.toImmutableList(),
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
