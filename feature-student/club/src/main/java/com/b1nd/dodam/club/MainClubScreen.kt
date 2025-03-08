package com.b1nd.dodam.club

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.b1nd.dodam.club.model.MyClubPage
import com.b1nd.dodam.club.screen.JoinClubScreen
import com.b1nd.dodam.club.screen.MyClubScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MyClubScreen(viewModel: MyClubViewModel = koinViewModel(), popBackStack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var nowPage by remember { mutableStateOf(MyClubPage.MY) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = nowPage == MyClubPage.MY,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            MyClubScreen(
                popBackStack = popBackStack,
                sideEffect = state.clubSideEffect,
                onNavigateToJoin = {
                    nowPage = MyClubPage.JOIN
                },
            )
        }
        AnimatedVisibility(
            visible = nowPage == MyClubPage.JOIN,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            JoinClubScreen(
                popBackStack = {
                    viewModel.getClub()
                    nowPage = MyClubPage.MY
                },
                onNavigateToJoin = {
                    nowPage = MyClubPage.MY
                },
            )
        }
    }
}
