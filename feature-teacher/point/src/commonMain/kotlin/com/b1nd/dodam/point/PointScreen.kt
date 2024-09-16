package com.b1nd.dodam.point

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.component.DodamSegment
import com.b1nd.dodam.point.screen.GiveScreen
import com.b1nd.dodam.point.screen.SelectScreen
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun PointScreen(
    viewModel: PointViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var nowPage by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = nowPage == 0,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            SelectScreen(
                items = uiState.students,
                onClickStudent = viewModel::clickStudent,
                onClickNextPage = {
                    nowPage = 1
                },
                popBackStack = {}
            )
        }
        AnimatedVisibility(
            visible = nowPage == 1,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            GiveScreen(
                studentList = uiState.students,
                reasonList = uiState.reasons,
                onClickGivePoint = { students, reason ->
                    viewModel.givePoint(
                        students = students,
                        reason = reason
                    )
                },
                popBackStack = {
                    nowPage = 0
                },
            )
        }
        if (uiState.loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DodamTheme.colors.labelNormal.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                DodamLoadingDots()
            }
        }
    }
}

internal fun getDodamSegment(text: String, selectText: String, onClick: (String) -> Unit) =
    DodamSegment(
        selected = text == selectText,
        onClick = {
            onClick(text)
        },
        text = text,
        enabled = true
    )