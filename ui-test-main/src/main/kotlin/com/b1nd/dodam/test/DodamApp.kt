package com.b1nd.dodam.test

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme
import com.b1nd.dodam.student.main.navigation.MAIN_ROUTE
import com.b1nd.dodam.student.main.navigation.mainScreen

@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = MAIN_ROUTE,
        ) {
            mainScreen(
                navigateToAskNightStudy = {
                    TODO("navigate to add nightStudy screen")
                },
                navigateToAddOuting = {
                    TODO("navigate to add outing screen")
                },
                navigateToSetting = {
                },
                navigateToMyPoint = {
                },
                navigateToAddBus = {
                },
                navigateToSchedule = {
                },
                navigateToWakeUpSong = {
                },
                navigateToAddWakeUpSong = {
                },
            )
        }
    }
}
