package com.b1nd.dodam.home.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.b1nd.dodam.data.nightstudy.model.NightStudy
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.DodamLoadingDots
import com.b1nd.dodam.designsystem.foundation.DodamIcons
import com.b1nd.dodam.home.DefaultText
import com.b1nd.dodam.home.InnerCountCard
import com.b1nd.dodam.home.model.NightStudyUiState
import com.b1nd.dodam.ui.component.DodamContainer

@Composable
internal fun NightStudyCard(
    uiState: NightStudyUiState,
    showShimmer: Boolean,
    onRefreshClick: () -> Unit,
    onContentClick: () -> Unit
) {
    DodamContainer(
        icon = DodamIcons.MoonPlus,
        title = "심야 자습 현황"
    ) {
        if (!showShimmer) {
            when (uiState) {
                is NightStudyUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
                    ) {
                        InnerCountCard(
                            title = "현재 자습중인 학생",
                            content = "${uiState.active}명",
                            buttonText = "${uiState.pending}명 대기중",
                            onClick = onContentClick
                        )
                    }
                }
                NightStudyUiState.Error -> {
                    DefaultText(
                        onClick = onRefreshClick,
                        label = "심자 현황을 불러올 수 없어요",
                        body = "다시 불러오기"
                    )
                }
                NightStudyUiState.Loading -> {
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
