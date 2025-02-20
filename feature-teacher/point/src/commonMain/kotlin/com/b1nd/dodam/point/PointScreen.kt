package com.b1nd.dodam.point

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.point.model.PointPage
import com.b1nd.dodam.point.model.PointSideEffect
import com.b1nd.dodam.point.screen.GiveScreen
import com.b1nd.dodam.point.screen.SelectScreen
import com.b1nd.dodam.ui.component.SnackbarState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun PointScreen(viewModel: PointViewModel = koinViewModel(), showSnackbar: (state: SnackbarState, message: String) -> Unit, popBackStack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    var nowPage by remember { mutableStateOf(PointPage.SELECT) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        viewModel.sideEffect.collect {
            when (it) {
                is PointSideEffect.FailedGivePoint -> {
                    showSnackbar(SnackbarState.ERROR, it.throwable.message ?: "상벌점 부여에 실패했습니다.")
                }
                is PointSideEffect.SuccessGivePoint -> {
                    showSnackbar(SnackbarState.SUCCESS, "상벌점 부여에 성공하였습니다.")
                    coroutineScope.launch {
                        nowPage = PointPage.SELECT
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedVisibility(
            visible = nowPage == PointPage.SELECT,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            SelectScreen(
                uiState = uiState.uiState,
                onClickStudent = viewModel::clickStudent,
                onClickNextPage = {
                    nowPage = PointPage.GIVE
                },
                reloadStudent = viewModel::load,
                popBackStack = popBackStack,
            )
        }
        AnimatedVisibility(
            visible = nowPage == PointPage.GIVE,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            GiveScreen(
                uiState = uiState.uiState,
                isNetworkLoading = uiState.isNetworkLoading,
                onClickGivePoint = { students, reason ->
                    viewModel.givePoint(
                        students = students,
                        reason = reason,
                    )
                },
                reload = viewModel::load,
                popBackStack = {
                    nowPage = PointPage.SELECT
                },
            )
        }
        if (uiState.isNetworkLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.labelNormal.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center,
            ) {
                DodamLoadingDots()
            }
        }
    }
}

internal fun getDodamSegment(text: String, selectText: String, onClick: (String) -> Unit) = DodamSegment(
    selected = text == selectText,
    onClick = {
        onClick(text)
    },
    text = text,
    enabled = true,
)
