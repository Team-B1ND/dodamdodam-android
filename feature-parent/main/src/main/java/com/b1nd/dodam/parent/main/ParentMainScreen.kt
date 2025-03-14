package com.b1nd.dodam.parent.main

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.b1nd.dodam.all.navigation.parentAllScreen
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamNavigationBar
import com.b1nd.dodam.designsystem.component.DodamNavigationBarItem
import com.b1nd.dodam.notice.navigation.noticeScreen
import com.b1nd.dodam.parent.childrenmanage.navigation.childrenManageScreen
import com.b1nd.dodam.parent.childrenmanage.navigation.navigateToChildrenManageScreen
import com.b1nd.dodam.parnet.home.navigation.PARENT_HOME_ROUTE
import com.b1nd.dodam.parnet.home.navigation.parentHomeScreen
import com.b1nd.dodam.register.navigation.navigateToInfo
import kotlinx.collections.immutable.toImmutableList

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
internal fun ParentMainScreen(
    navController: NavHostController,
    navigateToMeal: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToNoticeViewer: (startIndex: Int, images: String) -> Unit,
    navigateToGroup: () -> Unit,
    showToast: (String, String) -> Unit,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }
    val mainScreenState = rememberParentMainScreenState(navController)
    var bottomNavVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        NavHost(
            navController = navController,
            startDestination = PARENT_HOME_ROUTE,
        ) {
            parentHomeScreen(
                navigateToMeal = navigateToMeal,
            )
            noticeScreen(
                isTeacher = false,
                navigateToNoticeCreate = null,
                navigateToNoticeViewer = navigateToNoticeViewer,
                changeBottomNavVisible = { visible ->
                    bottomNavVisible = visible
                },
            )
            parentAllScreen(
                navigateToSetting = navigateToSetting,
                navigateToChildrenManage = navController::navigateToChildrenManageScreen,
                navigateToGroup = navigateToGroup,
            )
            childrenManageScreen(
                popBackStack = navController::popBackStack,
                changeBottomNavVisible = { visible ->
                    bottomNavVisible = visible
                },
                navigateToInfo = {
                    navController.navigateToInfo()
                },
            )
        }
        if (bottomNavVisible) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                DodamTheme.colors.staticWhite.copy(alpha = 0f),
                                DodamTheme.colors.staticWhite,
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
