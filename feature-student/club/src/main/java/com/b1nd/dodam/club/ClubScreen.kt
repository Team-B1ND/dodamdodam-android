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
import com.b1nd.dodam.club.model.ClubPage
import com.b1nd.dodam.club.screen.JoinClubScreen
import com.b1nd.dodam.club.screen.MyClubScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ClubScreen(viewModel: ClubViewModel = koinViewModel(), popBackStack: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var nowPage by remember { mutableStateOf(ClubPage.MY) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        AnimatedVisibility(
            visible = nowPage == ClubPage.MY,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            MyClubScreen(
                popBackStack = popBackStack,
                sideEffect = state.clubSideEffect,
                onNavigateToJoin = {
                    nowPage = ClubPage.JOIN
                },
            )
        }
        AnimatedVisibility(
            visible = nowPage == ClubPage.JOIN,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            JoinClubScreen(
                popBackStack = {
                    viewModel.getClub()
                    nowPage = ClubPage.MY
                },
                onNavigateToJoin = {
                    nowPage = ClubPage.MY
                }
            )
        }
    }
}


