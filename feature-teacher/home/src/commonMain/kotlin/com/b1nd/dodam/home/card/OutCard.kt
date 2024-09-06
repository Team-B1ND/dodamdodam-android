package com.b1nd.dodam.home.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.DefaultText
import com.b1nd.dodam.home.InnerCountCard
import com.b1nd.dodam.home.model.OutUiState
import com.b1nd.dodam.ui.component.DodamContainer

@Composable
internal fun OutCard(showShimmer: Boolean, uiState: OutUiState, onRefreshClick: () -> Unit, onOutingClick: () -> Unit, onSleepoverClick: () -> Unit) {
    DodamContainer(
        icon = DodamIcons.DoorOpen,
        title = "외출/외박 현황",
    ) {
        if (!showShimmer) {
            when (uiState) {
                is OutUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth(),
                    ) {
                        InnerCountCard(
                            title = "현재 외출중인 학생",
                            content = "${uiState.outAllowCount}명",
                            buttonText = "${uiState.outPendingCount}명 대기중",
                            onClick = onOutingClick,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        InnerCountCard(
                            title = "현재 외박중인 학생",
                            content = "${uiState.sleepoverAllowCount}명",
                            buttonText = "${uiState.sleepoverPendingCount}명 대기중",
                            onClick = onSleepoverClick,
                        )
                    }
                }
                is OutUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        DodamLoadingDots()
                    }
                }
                is OutUiState.Error -> {
                    DefaultText(
                        onClick = onRefreshClick,
                        label = "외출/외박 현황을 불러올 수 없어요",
                        body = "다시 불러오기",
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                DodamLoadingDots()
            }
        }
    }
}
