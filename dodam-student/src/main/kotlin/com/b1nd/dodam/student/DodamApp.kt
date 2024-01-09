package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamdodamandroidTheme

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamdodamandroidTheme {
        NavHost(
            navController = navController,
            startDestination = "",
        ) {
            // TODO : Add navigation composable
        }
    }
}
