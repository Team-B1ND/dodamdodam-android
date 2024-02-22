package com.b1nd.dodam.student.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.component.BottomNavigationItem
import com.b1nd.dodam.designsystem.component.DodamBottomNavigation
import com.b1nd.dodam.designsystem.icons.Calendar
import com.b1nd.dodam.designsystem.icons.ForkAndKnife
import com.b1nd.dodam.designsystem.icons.Home
import com.b1nd.dodam.designsystem.icons.More
import com.b1nd.dodam.designsystem.icons.Out
import com.b1nd.dodam.meal.navigation.MEAL_ROUTE
import com.b1nd.dodam.student.home.navigation.HOME_ROUTE
import com.b1nd.dodam.student.home.navigation.homeScreen

@Composable
internal fun MainScreen(navController: NavHostController = rememberNavController()) {
    val bottomNavItems = listOf(
        BottomNavigationItem(HOME_ROUTE, Home),
        BottomNavigationItem(MEAL_ROUTE, ForkAndKnife),
        BottomNavigationItem("", Out),
        BottomNavigationItem("", Calendar),
        BottomNavigationItem("", More),
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = HOME_ROUTE
        ) {
            homeScreen()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0f),
                            MaterialTheme.colorScheme.surface,
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            DodamBottomNavigation(
                navController = navController,
                bottomNavigationItems = bottomNavItems
            )
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}
