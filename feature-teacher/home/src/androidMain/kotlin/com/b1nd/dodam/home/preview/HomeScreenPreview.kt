package com.b1nd.dodam.home.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.home.HomeScreen

@Composable
@Preview
fun HomeScreenPreview() {
    DodamTheme {
        HomeScreen()
    }
}