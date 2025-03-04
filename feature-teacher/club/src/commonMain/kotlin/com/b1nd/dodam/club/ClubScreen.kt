@file:Suppress("UNREACHABLE_CODE")

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
import com.b1nd.dodam.club.model.ClubSideEffect
import com.b1nd.dodam.club.screen.ClubDetailScreen
import com.b1nd.dodam.club.screen.ClubListScreen
import com.b1nd.dodam.ui.component.SnackbarState
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI


@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun ClubScreen(
    viewModel: ClubViewModel = koinViewModel(),
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
    popBackStack: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    var nowPage by remember { mutableStateOf(ClubPage.LIST) }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect {
            when (it) {
                is ClubSideEffect.Failed -> {
                    showSnackbar(SnackbarState.ERROR, it.throwable.message ?: "동아리 개설에 실패했습니다.")
                }

                is ClubSideEffect.SuccessApprove -> {
                    viewModel.loadClubList()
                    showSnackbar(SnackbarState.SUCCESS, "동아리 개설을 승인했습니다.")
                }

                is ClubSideEffect.SuccessReject -> {
                    viewModel.loadClubList()
                    showSnackbar(SnackbarState.SUCCESS, "동아리 개설을 거절했습니다.")
                }
            }
        }
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
                popBackStack = popBackStack,
                selectClubList = { id, name, type, shortDescription ->
                    viewModel.detailMember(id, name, type, shortDescription)
                },
                selectDetailClub = { id, club ->
                    nowPage = ClubPage.DETAIL
                    viewModel.loadDetailClub(id, club)
                },
                selectAllowButton = {id, state,reason ->
                    viewModel.postClubState(id.toInt(),state, reason)
                }
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
                }
            )
        }
    }
}
