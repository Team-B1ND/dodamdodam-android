package com.b1nd.dodam.club

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.b1nd.dodam.club.model.ClubPage
import com.b1nd.dodam.club.screen.ClubDetailScreen
import com.b1nd.dodam.club.screen.ClubListScreen
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ClubScreen(viewModel: ClubViewModel = koinViewModel(), popBackStack: () -> Unit, navigateToApply: () -> Unit) {
    val state by viewModel.state.collectAsState()
    var nowPage by remember { mutableStateOf(ClubPage.LIST) }

    LaunchedEffect(Unit) {
        viewModel.loadClubList()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = nowPage == ClubPage.LIST,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            ClubListScreen(
                state = state,
                selectDetailClub = { id, club ->
                    nowPage = ClubPage.DETAIL
                    viewModel.loadDetailClub(id, club)
                },
                navigateToApply = navigateToApply,
            )
        }
        AnimatedVisibility(
            visible = nowPage == ClubPage.DETAIL,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            ClubDetailScreen(
                state = state,
                popBackStack = {
                    viewModel.loadClubList()
                    nowPage = ClubPage.LIST
                },
                navigateToApply = navigateToApply,
            )
        }
    }
}
