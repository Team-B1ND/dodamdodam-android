package com.b1nd.dodam.nightstudy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.b1nd.dodam.designsystem.component.DodamAskCard
import com.b1nd.dodam.designsystem.component.DodamFullWidthButton
import com.b1nd.dodam.designsystem.component.DodamTopAppBar
import com.b1nd.dodam.designsystem.icons.Plus
import com.b1nd.dodam.designsystem.icons.SmailMoon
import com.b1nd.dodam.nightstudy.viewmodel.NightStudyViewModel
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlinx.datetime.toJavaLocalDateTime

@Composable
fun NightStudyScreen(onAddClick: () -> Unit, viewModel: NightStudyViewModel = hiltViewModel()) {
    val current = LocalDateTime.now()
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DodamTopAppBar(
            title = "심야 자습",
            containerColor = MaterialTheme.colorScheme.surface,
            icon = Plus,
            onIconClick = onAddClick
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp),
        ) {
            uiState.nightStudy?.let { nightStudyList ->
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(uiState.nightStudy!!.size) { index ->
                        val nightStudy = uiState.nightStudy!![index]

                        val nightStudyProgress = 1 - ChronoUnit.SECONDS.between(
                            nightStudy.startAt.toJavaLocalDateTime(),
                            current,
                        ).toFloat() / ChronoUnit.SECONDS.between(
                            nightStudy.startAt.toJavaLocalDateTime(),
                            nightStudy.endAt.toJavaLocalDateTime(),
                        )

                        val day = ChronoUnit.DAYS.between(
                            current,
                            nightStudy.endAt.toJavaLocalDateTime(),
                        )
                        val hour = ChronoUnit.HOURS.between(
                            current,
                            nightStudy.endAt.toJavaLocalDateTime(),
                        )
                        val minute =
                            ChronoUnit.MINUTES.between(
                                current,
                                nightStudy.endAt.toJavaLocalDateTime(),
                            )

                        val currentLeftTime =
                            if (day > 0) {
                                "${day}일 "
                            } else if (hour > 0) {
                                "${hour}시간 "
                            } else {
                                "${minute}분 "
                            }

                        DodamAskCard(
                            reason = nightStudy.content,
                            askStatus = nightStudy.status.name,
                            phoneReason = nightStudy.reasonForPhone,
                            startTimeText = "시작",
                            startTime = nightStudy.startAt.monthNumber.toString() + "월 " + nightStudy.startAt.dayOfMonth + "일",
                            endTimeText = "종료",
                            endTime = nightStudy.endAt.monthNumber.toString() + "월 " + nightStudy.endAt.dayOfMonth + "일",
                            currentLeftTime = currentLeftTime,
                            progress = nightStudyProgress,
                        )
                    }
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            RoundedCornerShape(18.dp),
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Image(imageVector = SmailMoon, contentDescription = null)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "신청한 심야 자습이 없어요",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.tertiary,
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        DodamFullWidthButton(
                            onClick = { onAddClick() },
                            text = "심야 자습 신청하기",
                            colors = ButtonColors(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.onSurfaceVariant,
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }
                }
            }
        }
    }
}
