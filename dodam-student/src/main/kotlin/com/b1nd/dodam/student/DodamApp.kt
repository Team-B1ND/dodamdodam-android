package com.b1nd.dodam.student

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.b1nd.dodam.designsystem.theme.DodamTheme

@Composable
fun DodamApp(navController: NavHostController = rememberNavController()) {
    DodamTheme {
        NavHost(
            navController = navController,
            startDestination = "",
        ) {
            // TODO : Add navigation composable
        }
    }
}
