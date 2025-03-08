package com.b1nd.dodam.club

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.b1nd.dodam.club.model.MyClubPage
import com.b1nd.dodam.club.screen.JoinClubScreen
import com.b1nd.dodam.club.screen.MyClubScreen
import com.b1nd.dodam.ui.component.SnackbarState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MyClubScreen(
    viewModel: MyClubViewModel = koinViewModel(),
    popBackStack: () -> Unit,
    showSnackbar: (state: SnackbarState, message: String) -> Unit,
) {
    var nowPage by remember { mutableStateOf(MyClubPage.MY) }

    LaunchedEffect(true) {
        viewModel.sideEffect.collect {
            when (it) {
                is ApplySideEffect.Failed -> {
                    showSnackbar(SnackbarState.ERROR, it.throwable.message ?: "동아리 개설에 실패했습니다.")
                }
                ApplySideEffect.SuccessApply -> {
                    showSnackbar(SnackbarState.SUCCESS, "성공하였습니다.")
                }
                ApplySideEffect.SuccessReject -> {
                    showSnackbar(SnackbarState.SUCCESS, "거절하였습니다.")
                }
            }
        }
    }

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
